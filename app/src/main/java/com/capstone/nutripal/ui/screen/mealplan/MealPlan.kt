package com.capstone.nutripal.ui.screen.mealplan

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.components.cards.DailyCardAnalysis
import com.capstone.nutripal.ui.components.cards.HandleCourse
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White
import kotlinx.coroutines.launch
import com.capstone.nutripal.model.DataTracker
import com.capstone.nutripal.ui.components.general.MealPlanNotFound
import com.capstone.nutripal.ui.theme.secondText
import com.capstone.nutripal.ui.theme.shadow


@Composable
fun MealPlan(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current as ComponentActivity,
    dataStore: StoreDataUser = StoreDataUser(context),
    mealPlanViewModel: MealPlanViewModel = viewModel(
        factory = ViewModelFactory(dataStore, FakeFoodRepository())
    ),
    navigateToDetail: (String, String) -> Unit,
    onFindSomeFood: () -> Unit,
) {
    val userToken = dataStore.getUserJwtToken().collectAsState(initial = "")

    mealPlanViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                if (userToken.value != "") {
                    mealPlanViewModel.viewModelScope.launch {
                        mealPlanViewModel.getDayTracker(userToken.value)
                    }
                }
            }
            is UiState.Success -> {
                val data = uiState.data
                println("ini data meal plan")
                println(data.mealPlan)
                if (userToken.value != "") {
                    MealPlanContent(data, userToken.value, mealPlanViewModel, navigateToDetail, onFindSomeFood)
                }
            }
            is UiState.Error -> {}
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MealPlanContent (
    data: DataTracker,
    userToken: String,
    mealPlanViewModel: MealPlanViewModel,
    navigateToDetail: (String, String) -> Unit,
    onFindSomeFood: () -> Unit,
) {

    val isMenuOpen = remember { mutableStateOf(false) }
    val menuItemsContent = listOf("Re-generate meal plan")
    val selectedItem = remember { mutableStateOf("") }
    println("INI MEALPLAN LUNCH DI MEALPLANCONTENT")
    println(data.mealPlan.lunch)

    NutriPalTheme {
       Scaffold(
           topBar = {
               TopAppBar(
                   title = {
                       Text(
                           "Daily Meal Plan",
                           style = MaterialTheme.typography.h1
                       )
                           },
                   backgroundColor = White,
                   elevation = 1.dp
               )
                    },
       ) {
           LazyColumn(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(16.dp)
           ) {
               item {
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.SpaceBetween,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           "Today's Meal Plan",
                           style = MaterialTheme.typography.body2
                       )
                       // for the dropdown
                       Box() {
                           Icon(
                               imageVector = Icons.Filled.MoreVert,
                               contentDescription = null,
                               tint = secondText,
                               modifier = Modifier
                                   .clickable { isMenuOpen.value = true }
                           )
                           DropdownMenu(
                               expanded = isMenuOpen.value,
                               onDismissRequest = { isMenuOpen.value = false },
                               modifier = Modifier.border(0.5.dp, shadow, RectangleShape)
                           ) {
                               menuItemsContent.forEach { item ->
                                   DropdownMenuItem(onClick = {
                                       selectedItem.value = item
                                       isMenuOpen.value = false
                                   }) {
                                       Text(item)
                                   }
                               }
                           }
                       }
                   }
                   Spacer(modifier = Modifier
                       .fillMaxWidth()
                       .height(5.dp))
                   DailyCardAnalysis(
                       calorie = 10.0,
                       calorieNeeded = 0.0,
                       protein = 10.0,
                       proteinNeeded = 0.0,
                       carbs = 10.0,
                       carbsNeeded = 0.0,
                       fat = 10.0,
                       fatNeeded = 0.0,
                       isMealPlan = true,
                   )
                   Spacer(modifier = Modifier
                       .fillMaxWidth()
                       .height(10.dp))
                   if (data.mealPlan.breakfast.isEmpty() && data.mealPlan.lunch.isEmpty() && data.mealPlan.dinner.isEmpty()
                       && data.eatenFood.breakfast.isEmpty() && data.eatenFood.lunch.isEmpty() && data.eatenFood.dinner.isEmpty()
                   ) {
                       MealPlanNotFound(
                           onClickRecommend = {},
                           onAddSomeFood = {onFindSomeFood()}
                       )
                       Spacer(modifier = Modifier
                           .fillMaxWidth()
                           .height(20.dp))
                   } else {
                       Text(
                           "Breakfast",
                           style = MaterialTheme.typography.body2
                       )
                       for (item in data.mealPlan.breakfast) {
                           HandleCourse(
                               item,
                               item.foodId,
                               item.foodId,
                               "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                               item.foodName,
                               item.servingDescription,
                               false,
                               item.calories.toString(),
                               item.protein.toString(),
                               item.carbohydrate.toString(),
                               item.fat.toString(),
                               navigateToDetail = navigateToDetail,
                               onEat = {
                                   mealPlanViewModel.postEatenFood(userToken, item.id)
                               },
                               onUneat = {
                                   mealPlanViewModel.deleteEatenFood(userToken, item.id)
                               },
                               onDelete = {
                                   mealPlanViewModel.deleteFoodFromMealPlan(userToken, item.id)
                               },
                               onSwipeEat = {},
                               onSwipeUneat = {},
                           )
                       }
                       Spacer(modifier = Modifier
                           .fillMaxWidth()
                           .height(5.dp))
                       for (item in data.eatenFood.breakfast) {
                           HandleCourse(
                               item,
                               item.foodId,
                               item.foodId,
                               "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                               item.foodName,
                               item.servingDescription,
                               true,
                               item.calories.toString(),
                               item.protein.toString(),
                               item.carbohydrate.toString(),
                               item.fat.toString(),
                               navigateToDetail = navigateToDetail,
                               onEat = {
                                   mealPlanViewModel.postEatenFood(userToken, item.id)
                               },
                               onUneat = {
                                   mealPlanViewModel.deleteEatenFood(userToken, item.id)
                               },
                               onDelete = {
                                   mealPlanViewModel.deleteFoodFromMealPlan(userToken, item.id)
                               },
                               onSwipeEat = {},
                               onSwipeUneat = {},
                           )
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                       }
                       Text(
                           "Lunch",
                           style = MaterialTheme.typography.body2
                       )
                       for (item in data.mealPlan.lunch) {
                           println("ini mealplan lunch")
                           println(item)
                           HandleCourse(
                               item,
                               item.foodId,
                               item.foodId,
                               "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                               item.foodName,
                               item.servingDescription,
                               false,
                               item.calories.toString(),
                               item.protein.toString(),
                               item.carbohydrate.toString(),
                               item.fat.toString(),
                               navigateToDetail = navigateToDetail,
                               onEat = {
                                   mealPlanViewModel.postEatenFood(userToken, item.id)
                               },
                               onUneat = {
                                   mealPlanViewModel.deleteEatenFood(userToken, item.id)
                               },
                               onDelete = {
                                   mealPlanViewModel.deleteFoodFromMealPlan(userToken, item.id)
                               },
                               onSwipeEat = {},
                               onSwipeUneat = {},
                           )
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                       }
                       for (item in data.eatenFood.lunch) {
                           HandleCourse(
                               item,
                               item.foodId,
                               item.foodId,
                               "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                               item.foodName,
                               item.servingDescription,
                               true,
                               item.calories.toString(),
                               item.protein.toString(),
                               item.carbohydrate.toString(),
                               item.fat.toString(),
                               navigateToDetail = navigateToDetail,
                               onEat = {
                                   mealPlanViewModel.postEatenFood(userToken, item.id)
                               },
                               onUneat = {
                                   mealPlanViewModel.deleteEatenFood(userToken, item.id)
                               },
                               onDelete = {
                                   mealPlanViewModel.deleteFoodFromMealPlan(userToken, item.id)
                               },
                               onSwipeEat = {},
                               onSwipeUneat = {},
                           )
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                       }

                       Text(
                           "Dinner",
                           style = MaterialTheme.typography.body2
                       )
                       for (item in data.mealPlan.dinner) {
                           HandleCourse(
                               item,
                               item.foodId,
                               item.foodId,
                               "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                               item.foodName,
                               item.servingDescription,
                               false,
                               item.calories.toString(),
                               item.protein.toString(),
                               item.carbohydrate.toString(),
                               item.fat.toString(),
                               navigateToDetail = navigateToDetail,
                               onEat = {
                                   mealPlanViewModel.postEatenFood(userToken, item.id)
                               },
                               onUneat = {
                                   mealPlanViewModel.deleteEatenFood(userToken, item.id)
                               },
                               onDelete = {
                                   mealPlanViewModel.deleteFoodFromMealPlan(userToken, item.id)
                               },
                               onSwipeEat = {},
                               onSwipeUneat = {},
                           )
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                       }

                       for (item in data.eatenFood.dinner) {
                           HandleCourse(
                               item,
                               item.foodId,
                               item.foodId,
                               "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                               item.foodName,
                               item.servingDescription,
                               true,
                               item.calories.toString(),
                               item.protein.toString(),
                               item.carbohydrate.toString(),
                               item.fat.toString(),
                               navigateToDetail = navigateToDetail,
                               onEat = {
                                   mealPlanViewModel.postEatenFood(userToken, item.id)
                               },
                               onUneat = {
                                   mealPlanViewModel.deleteEatenFood(userToken, item.id)
                               },
                               onDelete = {
                                   mealPlanViewModel.deleteFoodFromMealPlan(userToken, item.id)
                               },
                               onSwipeEat = {},
                               onSwipeUneat = {},
                           )
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                       }
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
//        MealPlanContent()
    }
}
