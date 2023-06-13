package com.capstone.nutripal.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.capstone.nutripal.R
import com.capstone.nutripal.di.Injection
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.nutripal.model.OrderFakeFood
import com.capstone.nutripal.ui.components.cards.HomeCardAnalysis
import com.capstone.nutripal.ui.components.general.SearchBar
import com.capstone.nutripal.ui.components.badges.StatusChips
import com.capstone.nutripal.ui.components.cards.HandleCourse
import com.capstone.nutripal.ui.theme.*

@Composable
fun HomeScreen(
//    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    onSearchbarClicked: () -> Unit,
    navigateToMealPlan: () -> Unit,
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreDataUser(context)

//    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken(context.getString(R.string.default_web_client_id))
//        .requestEmail()
//        .build()
//    val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

    val viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(dataStore, Injection.provideRepository()))
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFoods()
            }
            is UiState.Success -> {
                HomeContent(
                    orderFoods = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onSearchbarClicked,
                    onSeeMoreClicked = navigateToMealPlan
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderFoods: List<OrderFakeFood>,
    navigateToDetail: (String) -> Unit,
    onSearchbarClicked: () -> Unit,
    onSeeMoreClicked: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            // section ijo bagian atas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(IjoCompo)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(IjoCompo)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(IjoCompo, shape = RoundedCornerShape(10.dp))
                    ) {
                        SearchBar(
                            hint = "what do you want to eat?",
                            isDummy = true,
                            onClick = onSearchbarClicked,
                        ) {
                        }
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Daily Analysis",
                                color = White,
                                style = MaterialTheme.typography.body2
                            )
                            StatusChips(
                                title = "On Target"
                            )

                        }
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp))
                        HomeCardAnalysis(
                            calorie = 100,
                            calorieNeeded = 1000,
                            protein = 100,
                            proteinNeeded = 1000,
                            carbs = 100,
                            carbsNeeded = 1000,
                            fat = 100,
                            fatNeeded = 1000
                        )
                    }
                }
            }
            // section putih bagian bawah
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Your Next Meal",
                            style = MaterialTheme.typography.body2
                        )
                        Row(
                            modifier = Modifier.wrapContentSize()
                                .clickable {
                                    onSeeMoreClicked()
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                "see more",
                                style = MaterialTheme.typography.subtitle1,
                                color = disabledText,
                                textDecoration = TextDecoration.Underline
                            )
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                tint = disabledText,
                                contentDescription = "Food Search"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    for(item: OrderFakeFood in orderFoods) {
                        // kalo belum dimakan masuk sini
                        if(!item.food.isEaten) {
                            HandleCourse(
                                item.food.id,
                                item.food.id,
                                "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                                item.food.foodTitle,
                                item.food.portion,
                                item.food.isEaten,
                                item.food.calorie,
                                item.food.protein,
                                item.food.carbs,
                                item.food.fat,
                                navigateToDetail = navigateToDetail
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Eaten Meal",
                            style = MaterialTheme.typography.body2
                        )
                        Row(
                            modifier = Modifier.wrapContentSize()
                                .clickable {
                                    onSeeMoreClicked()
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                "see more",
                                style = MaterialTheme.typography.subtitle1,
                                color = disabledText,
                                textDecoration = TextDecoration.Underline
                            )
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                tint = disabledText,
                                contentDescription = "Food Search"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    for(item: OrderFakeFood in orderFoods) {
                        // kalo udah dimakan
                        if(item.food.isEaten) {
                            HandleCourse(
                                item.food.id,
                                item.food.id,
                                "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                                item.food.foodTitle,
                                item.food.portion,
                                item.food.isEaten,
                                item.food.calorie,
                                item.food.protein,
                                item.food.carbs,
                                item.food.fat,
                                navigateToDetail = navigateToDetail
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting2(
    name: String,
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreDataUser(context)

    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

    Column() {
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.h1
        )
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.subtitle2
        )
        Button(
            onClick = {
                scope.launch {
//                    Firebase.auth.signOut()
                    googleSignInClient.signOut().await()
                    dataStore.logout()
                }
                navController.popBackStack()
                navController.navigate(Screen.Welcome.route)
            },
            shape = RoundedCornerShape(27.dp),
            contentPadding = PaddingValues(horizontal = 150.dp)
        ) {
            Text(
                text= "Logout",
                fontWeight = FontWeight(700)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    NutriPalTheme {
//        HomeScreen({},{})
    }
}