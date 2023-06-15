package com.capstone.nutripal.ui.screen.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.nutripal.di.Injection
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.theme.NutriPalTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.capstone.nutripal.ui.components.cards.HomeCardAnalysis
import com.capstone.nutripal.ui.components.general.SearchBar
import com.capstone.nutripal.ui.components.badges.StatusChips
import com.capstone.nutripal.ui.components.cards.HandleCourse
import com.capstone.nutripal.ui.screen.mealplan.MealPlanViewModel
import com.capstone.nutripal.ui.theme.*

@Composable
fun HomeScreen(
//    modifier: Modifier = Modifier,
    navigateToDetail: (String, String) -> Unit,
    onSearchbarClicked: () -> Unit,
    navigateToMealPlan: () -> Unit,
    context: Context = LocalContext.current,
    dataStore: StoreDataUser = StoreDataUser(context),
    homeViewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(dataStore, Injection.provideRepository())
    ),
    mealPlanViewModel: MealPlanViewModel = viewModel(
        factory = ViewModelFactory(dataStore, Injection.provideRepository())
    ),
) {
    val userToken = dataStore.getUserJwtToken().collectAsState(initial = "")

    val resultEatenFoods by homeViewModel.resultEatenFoods.collectAsState()
    val resultMealPlans by homeViewModel.resultMealPlans.collectAsState()
    val resultNutritionGoal by homeViewModel.resultNutritionGoal.collectAsState()

    val currentCalories by homeViewModel.currentCalories.collectAsState()
    val currentProtein by homeViewModel.currentProtein.collectAsState()
    val currentCarbs by homeViewModel.currentCarbs.collectAsState()
    val currentFat by homeViewModel.currentFat.collectAsState()

//    var currentCalories by remember { mutableStateOf(homeViewModel.currentCalories.value) }
//    var currentProtein by remember { mutableStateOf(homeViewModel.currentProtein.value) }
//    var currentCarbs by remember { mutableStateOf(homeViewModel.currentCarbs.value) }
//    var currentFat by remember { mutableStateOf(homeViewModel.currentFat.value) }

//    var currentCalories by remember { mutableStateOf(resultEatenNutrition.calories) }
//    var currentProtein by remember { mutableStateOf(resultEatenNutrition.protein) }
//    var currentCarbs by remember { mutableStateOf(resultEatenNutrition.carbohydrate) }
//    var currentFat by remember { mutableStateOf(resultEatenNutrition.fat) }

    LaunchedEffect(key1 = true) {
        if (userToken.value != "") homeViewModel.getAllFoods(userToken.value)
    }

    println("UNEATEN FOODS IN HOME")
    println(resultMealPlans)

    println("EATEN FOODS IN HOME")
    println(resultEatenFoods)

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
                            calorie = currentCalories,
                            calorieNeeded = resultNutritionGoal.calorieGoal,
                            protein = currentProtein,
                            proteinNeeded = resultNutritionGoal.proteinGoal,
                            carbs = currentCarbs,
                            carbsNeeded = resultNutritionGoal.carbohydrateGoal,
                            fat = currentFat,
                            fatNeeded = resultNutritionGoal.fatGoal
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
                            modifier = Modifier
                                .wrapContentSize()
                                .clickable {
                                    navigateToMealPlan()
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
                    val firstFoodItem = resultMealPlans?.firstOrNull()
                    if (firstFoodItem != null) {
                        HandleCourse(
                            firstFoodItem,
                            firstFoodItem.foodId,
                            firstFoodItem.id.toString(),
                            "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                            firstFoodItem.foodName,
                            firstFoodItem.servingDescription,
                            false,
                            firstFoodItem.calories.toString(),
                            firstFoodItem.protein.toString(),
                            firstFoodItem.carbohydrate.toString(),
                            firstFoodItem.fat.toString(),
                            navigateToDetail = navigateToDetail,
                            onSwipeEat = { food ->
                                homeViewModel.onEaten(food)
//                                currentCalories += food.calories.toInt()
//                                currentProtein += food.protein.toInt()
//                                currentCarbs += food.carbohydrate.toInt()
//                                currentFat += food.fat.toInt()
                            },
                            onSwipeUneat = { food ->
                                homeViewModel.onUneaten(food)
//                                currentCalories -= food.calories.toInt()
//                                currentProtein -= food.protein.toInt()
//                                currentCarbs -= food.carbohydrate.toInt()
//                                currentFat -= food.fat.toInt()
                            },
                            onEat = {
                                mealPlanViewModel.postEatenFood(userToken.value, firstFoodItem.id)
                            },
                            onUneat = {
                                mealPlanViewModel.deleteEatenFood(userToken.value, firstFoodItem.id)
                            },
                            onDelete = {
                                mealPlanViewModel.deleteFoodFromMealPlan(userToken.value, firstFoodItem.id)
                            },
                        )
                    }
//                    resultMealPlans?.forEach { foodItem ->
//                        // kalo belum dimakan masuk sini
//                        HandleCourse(
//                            foodItem,
//                            foodItem.foodId,
//                            foodItem.id.toString(),
//                            "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
//                            foodItem.foodName,
//                            foodItem.servingDescription,
//                            false,
//                            foodItem.calories.toString(),
//                            foodItem.protein.toString(),
//                            foodItem.carbohydrate.toString(),
//                            foodItem.fat.toString(),
//                            navigateToDetail = navigateToDetail,
//                            onSwipeEat = { food ->
//                                homeViewModel.onEaten(food)
//                            },
//                            onSwipeUneat = { food ->
//                                homeViewModel.onUneaten(food)
//                            },
//                            onEat = {
//                                mealPlanViewModel.postEatenFood(userToken.value, foodItem.id)
//                            },
//                            onUneat = {
//                                mealPlanViewModel.deleteEatenFood(userToken.value, foodItem.id)
//                            },
//                            onDelete = {
//                                mealPlanViewModel.deleteFoodFromMealPlan(userToken.value, foodItem.id)
//                            },
//                        )
//                    }
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
                            modifier = Modifier
                                .wrapContentSize()
                                .clickable {
                                    navigateToMealPlan()
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
                    resultEatenFoods?.forEach { foodItem ->
                        // kalo udah dimakan masuk sini
                        HandleCourse(
                            foodItem,
                            foodItem.foodId,
                            foodItem.id.toString(),
                            "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                            foodItem.foodName,
                            foodItem.servingDescription,
                            true,
                            foodItem.calories.toString(),
                            foodItem.protein.toString(),
                            foodItem.carbohydrate.toString(),
                            foodItem.fat.toString(),
                            navigateToDetail = navigateToDetail,
                            onSwipeEat = { food ->
                                homeViewModel.onEaten(food)
//                                currentCalories += food.calories.toInt()
//                                currentProtein += food.protein.toInt()
//                                currentCarbs += food.carbohydrate.toInt()
//                                currentFat += food.fat.toInt()
                            },
                            onSwipeUneat = { food ->
                                homeViewModel.onUneaten(food)
//                                currentCalories -= food.calories.toInt()
//                                currentProtein -= food.protein.toInt()
//                                currentCarbs -= food.carbohydrate.toInt()
//                                currentFat -= food.fat.toInt()
                            },
                            onEat = {
                                mealPlanViewModel.postEatenFood(userToken.value, foodItem.id)
                            },
                            onUneat = {
                                mealPlanViewModel.deleteEatenFood(userToken.value, foodItem.id)
                            },
                            onDelete = {
                                mealPlanViewModel.deleteFoodFromMealPlan(userToken.value, foodItem.id)
                            },
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
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