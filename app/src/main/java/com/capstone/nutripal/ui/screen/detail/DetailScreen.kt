package com.capstone.nutripal.ui.screen.detail

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.capstone.nutripal.di.Injection
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.components.cards.HandleCourse
import com.capstone.nutripal.ui.components.general.TableGizi
import com.capstone.nutripal.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    foodId: String,
    viewModel: DetailPageViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getRewardById(foodId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.food.photoUrl,
                    data.food.foodTitle,
                    data.food.desc,
                    data.count,
                    onBackClick = navigateBack,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailContent(
    image: String,
    title: String,
    desc: String,
    count: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bottomSheetScaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
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
                repeat(3) {
                    HandleCourse(
                        "1",
                        "Breakfast",
                        "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
                        "Soto Ayam",
                        "1 portion",
                        false,
                        700.0,
                        700.0,
                        700.0,
                        700.0,
                        {}
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "this food will exceed your daily nutrition needs",
                    style = MaterialTheme.typography.subtitle2,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.error
                )
                Spacer(modifier = Modifier.height(3.dp))
                Button(
                    onClick = {},
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
                        model = image,
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
                        text = "Judul Makanan",
                        style = MaterialTheme.typography.h1,
                    )
                    Text(
                        text = "Deskripsi ",
                        style = MaterialTheme.typography.body1,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Informasi Gizi",
                        style = MaterialTheme.typography.body2,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    // mulai table
                    TableGizi()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "this food will exceed your daily nutrition needs",
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.error
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                    bottomSheetScaffoldState.animateTo(ModalBottomSheetValue.Expanded)
                            }
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
                }
            }
        }
    }


}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    NutriPalTheme {
        DetailContent(
            "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
            "Jaket Hoodie Dicoding",
            "7500",
            1,
            onBackClick = {},
        )
    }
}