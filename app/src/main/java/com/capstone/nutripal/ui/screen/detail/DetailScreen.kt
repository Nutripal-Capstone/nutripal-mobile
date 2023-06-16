package com.capstone.nutripal.ui.screen.detail

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.capstone.nutripal.di.Injection
import com.capstone.nutripal.model.DataDetail
import com.capstone.nutripal.model.ProfileData
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.components.cards.Dialogs
import com.capstone.nutripal.ui.components.cards.HandleCourse
import com.capstone.nutripal.ui.components.general.TableGizi
import com.capstone.nutripal.ui.screen.signup.ActivityLevelOption
import com.capstone.nutripal.ui.screen.signup.SelectableCourse
import com.capstone.nutripal.ui.theme.*
import kotlinx.coroutines.launch

data class FoodTimeOption(
    val name: String,
    val description: String,
    val id: String
)

@Composable
fun DetailScreen(
    foodId: String,
    servingId: String,
    context: Context = LocalContext.current as ComponentActivity,
    dataStore: StoreDataUser = StoreDataUser(context),
    viewModel: DetailPageViewModel = viewModel(
        factory = ViewModelFactory(
            dataStore, Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToMealPlan : () -> Unit
) {
    val userToken = dataStore.getUserJwtToken().collectAsState(initial = "")

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                if (userToken.value != "") {
                    viewModel.viewModelScope.launch {
                        viewModel.getFoodById(userToken.value, foodId, servingId)
                    }
                }
            }
            is UiState.Success -> {
                if (userToken.value != "") {
                    val data = uiState.data
                    DetailContent(
                        data = data,
                        userToken = userToken.value,
                        onBackClick = navigateBack,
                        detailViewModel = viewModel,
                        navigateToMealPlan = navigateToMealPlan

                    )
                }
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
//    image: String,
    data : DataDetail,
    userToken: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel : DetailPageViewModel,
    navigateToMealPlan : () -> Unit
) {
    val bottomSheetScaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false)  }

    val activityLevelOptions = listOf(
        FoodTimeOption("Breakfast", "Will add to breakfast plan.", "breakfast"),
        FoodTimeOption("Lunch", "Will add to lunch plan.", "lunch"),
        FoodTimeOption("Dinner", "Will add to dinner plan.", "dinner"),
    )

    var selectedFoodTime by rememberSaveable { mutableStateOf("") }

    ModalBottomSheetLayout(
        sheetState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent =  {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Divider(
                        Modifier
                            .width(50.dp)
                            .clip(CircleShape), Color.LightGray, 4.dp)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Replace with:",
                    style = MaterialTheme.typography.h1,
                )
                Spacer(modifier = Modifier.height(6.dp))
                for (option in activityLevelOptions) {
                    SelectableCourse(
                        name = option.name,
                        description = option.description,
                        selected = selectedFoodTime == option.id,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedFoodTime = option.id
                            }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
//                Text(
//                    modifier = Modifier.fillMaxWidth(),
//                    text = "this food will exceed your daily nutrition needs",
//                    style = MaterialTheme.typography.subtitle2,
//                    textAlign = TextAlign.Center,
//                    color = MaterialTheme.colors.error
//                )
//                Spacer(modifier = Modifier.height(3.dp))
                Button(
                    onClick = {
                        openDialog.value = true
                    },
                    shape = RoundedCornerShape(27.dp),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 15.dp)
                ) {
                    Text(
                        text= "Replace with this food",
                        fontWeight = FontWeight(700)
                    )
                }

            }
        },
    ) {
        LazyColumn(modifier = Modifier) {
            item {
                Box {
                    AsyncImage(
                        model = "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                            .shadow(2.dp)
                    )
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF404040),
                                        Color(0xFF404040).copy(alpha = 0f)
                                    ),
                                )
                            )
                    ){
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Button",
                            tint = White,
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable { onBackClick() }
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = data.foodName.toString(),
                        style = MaterialTheme.typography.h1,
                    )
//                    Text(
//                        text = data.foodType.toString(),
//                        style = MaterialTheme.typography.body1,
//                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Nutrition Facts",
                        style = MaterialTheme.typography.body2,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    // mulai table
                    TableGizi(
                        serving_description = data.servingDescription.toString(),
                        calories = data.calories,
                        fat = data.fat,
                        saturated_fat = data.saturatedFat,
                        trans_fat = data.transFat,
                        polyunsaturated_fat = data.polyunsaturatedFat,
                        monounsaturated_fat = data.monounsaturatedFat,
                        cholesterol = data.cholesterol,
                        carbohydrates = data.carbohydrate,
                        protein = data.protein,
                        sodium = data.sodium,
                        potassium = data.potassium,
                        fiber = data.fiber,
                        sugar = data.sugar,
                        added_sugars = data.addedSugars,
                        vitamin_d = data.vitaminD,
                        vitamin_a = data.vitaminA,
                        vitamin_c = data.vitaminC,
                        calcium = data.calcium,
                        iron = data.iron
                    )
                    Spacer(modifier = Modifier.height(12.dp))
//                    Text(
//                        modifier = Modifier.fillMaxWidth(),
//                        text = "this food will exceed your daily nutrition needs",
//                        style = MaterialTheme.typography.subtitle2,
//                        textAlign = TextAlign.Center,
//                        color = MaterialTheme.colors.error
//                    )
//                    Spacer(modifier = Modifier.height(3.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
//                                    bottomSheetScaffoldState.animateTo(ModalBottomSheetValue.Expanded)
                                bottomSheetScaffoldState.show()
                            }
//                                  openDialog.value = true
                        },
                        shape = RoundedCornerShape(27.dp),
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = 15.dp)

                    ) {
                        Text(
                            text= "I want to eat this today",
                            fontWeight = FontWeight(700)
                        )
                    }
                    if (openDialog.value) {
                        androidx.compose.material3.AlertDialog(
                            onDismissRequest = {
                                openDialog.value = false
                            },
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(20.dp))
                                .background(White),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(16.dp, 24.dp, 16.dp, 16.dp),
                                ) {
                                    Text(
                                        text = "Want to add this to your plan?",
                                        style = MaterialTheme.typography.body2,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Warning! This action may surpass the intended nutritional limit.",
                                        style = MaterialTheme.typography.subtitle1,
                                        textAlign = TextAlign.Center
                                    )
//                                    Toast(this, "Mew")
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        androidx.compose.material3.Button(
                                            onClick = {
                                                detailViewModel.addToMealPlan(userToken, data, selectedFoodTime)
                                                navigateToMealPlan()
                                            },
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                                containerColor = IjoCompo,
                                            )
                                        ) {
                                            Text(
                                                text = "Yes, add this food to the plan",
                                                style = MaterialTheme.typography.body2,
                                                color = White
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        TextButton(
                                            onClick = {
                                                openDialog.value = false
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Cancel",
                                                style = MaterialTheme.typography.body2,
                                                color = IjoCompo
                                            )
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    NutriPalTheme {
//        DetailContent(
//            "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
//            "Jaket Hoodie Dicoding",
//            "7500",
//            1,
//            onBackClick = {},
//        )
    }
}