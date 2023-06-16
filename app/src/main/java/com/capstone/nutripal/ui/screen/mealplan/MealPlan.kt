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
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
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
import kotlinx.coroutines.launch
import com.capstone.nutripal.model.DataTracker
import com.capstone.nutripal.ui.components.general.MealPlanNotFound
import com.capstone.nutripal.ui.theme.*


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
    val loadingStateRecom by mealPlanViewModel.loadingStateRecom.collectAsState()

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
                if (userToken.value != "") {
                    MealPlanContent(loadingStateRecom, data, userToken.value, mealPlanViewModel, navigateToDetail, onFindSomeFood)
                }
            }
            is UiState.Error -> {}
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MealPlanContent (
    isLoading: Boolean,
    data: DataTracker,
    userToken: String,
    mealPlanViewModel: MealPlanViewModel,
    navigateToDetail: (String, String) -> Unit,
    onFindSomeFood: () -> Unit,
) {

    val isMenuOpen = remember { mutableStateOf(false) }
    val menuItemsContent = listOf("Re-generate meal plan")
    val selectedItem = remember { mutableStateOf("") }

    if (selectedItem.value == "Re-generate meal plan") {
        mealPlanViewModel.getRecommendation(userToken)
        selectedItem.value = ""
    }

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
                       Box {
                           Icon(
                               imageVector = Icons.Filled.Autorenew,
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
                       calorie = data.wholeNutrition.calories.toString(),
                       calorieNeeded = "",
                       protein = data.wholeNutrition.protein.toString(),
                       proteinNeeded = "",
                       carbs = data.wholeNutrition.carbohydrate.toString(),
                       carbsNeeded = "",
                       fat = data.wholeNutrition.fat.toString(),
                       fatNeeded = "",
                       isMealPlan = true,
                   )
                   Spacer(modifier = Modifier
                       .fillMaxWidth()
                       .height(10.dp))
                   if (isLoading) {
                       Box(
                           modifier = Modifier.fillMaxSize()
                               .padding(16.dp),
                       ) {
                           Box(
                               modifier = Modifier
                                   .wrapContentSize()
                                   .align(Alignment.Center)
                           ) {
                               CircularProgressIndicator(
                                   modifier = Modifier.size(50.dp),
                               )
                           }
                       }
                   } else {
                       if (data.mealPlan.breakfast.isEmpty() && data.mealPlan.lunch.isEmpty() && data.mealPlan.dinner.isEmpty()
                           && data.eatenFood.breakfast.isEmpty() && data.eatenFood.lunch.isEmpty() && data.eatenFood.dinner.isEmpty()
                       ) {
                           MealPlanNotFound(
                               onClickRecommend = {
                                   mealPlanViewModel.getRecommendation(userToken)
                               },
                               onAddSomeFood = {onFindSomeFood()}
                           )
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(20.dp))
                       } else {
                           JudulPlan("Breakfast") {
                               mealPlanViewModel.getRecommendation(userToken, "breakfast")
                           }
                           Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                           for (item in data.mealPlan.breakfast) {
                               HandleCourse(
                                   "mealplan",
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
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                           for (item in data.eatenFood.breakfast) {
                               HandleCourse(
                                   "mealplan",
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
                           Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                           JudulPlan("Lunch") {
                               mealPlanViewModel.getRecommendation(userToken, "lunch")
                           }
                           Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                           for (item in data.mealPlan.lunch) {
                               HandleCourse(
                                   "mealplan",
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
                                   "mealplan",
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

                           Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                           JudulPlan("Dinner") {
                               mealPlanViewModel.getRecommendation(userToken, "dinner")
                           }
                           Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                           for (item in data.mealPlan.dinner) {
                               HandleCourse(
                                   "mealplan",
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
                                   "mealplan",
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
                           Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))

                           Row(
                               modifier = Modifier.fillMaxWidth(),
                               horizontalArrangement = Arrangement.Center
                           ) {
                               Text(
                                   text = "Find Food to Add",
                                   style = MaterialTheme.typography.body2,
                                   color = IjoCompo,
                                   textDecoration = TextDecoration.Underline,
                                   modifier = Modifier.clickable {
                                       onFindSomeFood()
                                   }
                               )
                           }
                       }
                   }
               }
           }
       }
    }
}

@Composable
fun JudulPlan(
    judul: String,
    onRegenerate: () -> Unit
) {
    val menuRegeneratePlan = listOf("Re-generate")
    val selectedPlanToRegen = remember { mutableStateOf("") }
    val isMenuOpen = remember { mutableStateOf(false) }

    if(selectedPlanToRegen.value === "Re-generate") {
        onRegenerate()
    }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            judul,
            style = MaterialTheme.typography.body2
        )

        Box() {
            Icon(
                imageVector = Icons.Filled.Autorenew,
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
                menuRegeneratePlan.forEach { item ->
                    DropdownMenuItem(onClick = {
                        selectedPlanToRegen.value = item
                        isMenuOpen.value = false
                    }) {
                        Text(item)
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
