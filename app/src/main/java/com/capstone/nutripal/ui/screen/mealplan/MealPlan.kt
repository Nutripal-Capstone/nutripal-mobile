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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.capstone.nutripal.model.DataDayTracker
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.components.cards.DailyCardAnalysis
import com.capstone.nutripal.ui.components.cards.HandleCourse
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import com.capstone.nutripal.ui.components.general.MealPlanNotFound
import com.capstone.nutripal.ui.theme.*
import kotlinx.coroutines.launch


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
    mealPlanViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                mealPlanViewModel.viewModelScope.launch {
                    mealPlanViewModel.getDayTracker()
                }
            }
            is UiState.Success -> {
                val data = uiState.data
                println("ini data meal plan")
                println(data.mealPlan)
                MealPlanContent(data, mealPlanViewModel, navigateToDetail, onFindSomeFood)
            }
            is UiState.Error -> {}
            else -> {}
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MealPlanContent (
    data: DataDayTracker,
    mealPlanViewModel: MealPlanViewModel,
    navigateToDetail: (String, String) -> Unit,
    onFindSomeFood: () -> Unit,
) {
    val isMenuOpen = remember { mutableStateOf(false) }
    val menuItemsContent = listOf("Re-generate meal plan")
    val selectedItem = remember { mutableStateOf("") }

    NutriPalTheme() {
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
               item() {
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
                   println(data.wholeNutrition)
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
                   if (data.mealPlan?.breakfast?.size === 0 && data.mealPlan.dinner?.size === 0 && data.mealPlan.dinner?.size === 0
                       && data.eatenFood?.breakfast?.size === 0 && data.eatenFood.dinner?.size === 0 && data.eatenFood.dinner?.size === 0) {
                       MealPlanNotFound(
                           onClickRecommend = {},
                           onAddSomeFood = {onFindSomeFood()}
                       )
                       Spacer(modifier = Modifier
                           .fillMaxWidth()
                           .height(20.dp))
                   } else {
                       judulPlan("Breakfast", {
                           // TODO input on regenerate meal
                       })
                       Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                       for (item in data.mealPlan?.breakfast!!) {
                          if (item != null) {
                              HandleCourse(
                                  item.foodId.toString(),
                                  item.foodId.toString(),
                                  "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                                  item.foodName.toString(),
                                  item.servingDescription.toString(),
                                  false,
                                  item.calories.toString(),
                                  item.protein.toString(),
                                  item.carbohydrate.toString(),
                                  item.fat.toString(),
                                  navigateToDetail = {
                                      item.foodId?.let { it1 -> item.servingId?.let { it2 ->
                                          navigateToDetail(it1,
                                              it2
                                          )
                                      } }
                                  },
                                  onEat = {
                                      item.id?.let { it1 -> mealPlanViewModel.postEatenFood(it1) }
                                  },
                                  onUneat = {
                                      item.id?.let { it1 -> mealPlanViewModel.deleteEatenFood(it1) }
                                  },
                                  onDelete = {
                                      item.id?.let { it1 -> mealPlanViewModel.deleteFoodFromMealPlan(it1) }
                                  }


                              )
                          }
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                      }
                       for (item in data.eatenFood?.breakfast!!) {
                           if (item != null) {
                               HandleCourse(
                                   item.foodId.toString(),
                                   item.foodId.toString(),
                                   "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                                   item.foodName.toString(),
                                   item.servingDescription.toString(),
                                   true,
                                   item.calories.toString(),
                                   item.protein.toString(),
                                   item.carbohydrate.toString(),
                                   item.fat.toString(),
                                   navigateToDetail = {
                                       item.foodId?.let { it1 -> item.servingId?.let { it2 ->
                                           navigateToDetail(it1,
                                               it2
                                           )
                                       } }
                                   },
                                   onEat = {
                                       item.id?.let { it1 -> mealPlanViewModel.postEatenFood(it1) }
                                   },
                                   onUneat = {
                                      item.id?.let { it1 -> mealPlanViewModel.deleteEatenFood(it1) }
                                  },
                                   onDelete = {
                                      item.id?.let { it1 -> mealPlanViewModel.deleteFoodFromMealPlan(it1) }
                                  }
                               )
                           }
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                       }
                       Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                       judulPlan("Lunch", {
                           // TODO input on regenerate meal
                       })
                       Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                       for (item in data.mealPlan?.lunch!!) {
                           if (item != null) {
                               HandleCourse(
                                   item.foodId.toString(),
                                   item.foodId.toString(),
                                   "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                                   item.foodName.toString(),
                                   item.servingDescription.toString(),
                                   false,
                                   item.calories.toString(),
                                   item.protein.toString(),
                                   item.carbohydrate.toString(),
                                   item.fat.toString(),
                                   navigateToDetail = {
                                       item.foodId?.let { it1 -> item.servingId?.let { it2 ->
                                           navigateToDetail(it1,
                                               it2
                                           )
                                       } }
                                   },
                                   onEat = {
                                       item.id?.let { it1 -> mealPlanViewModel.postEatenFood(it1) }
                                   },
                                   onUneat = {
                                      item.id?.let { it1 -> mealPlanViewModel.deleteEatenFood(it1) }
                                  },
                                   onDelete = {
                                      item.id?.let { it1 -> mealPlanViewModel.deleteFoodFromMealPlan(it1) }
                                  }
                               )
                           }
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                       }
                       for (item in data.eatenFood?.lunch!!) {
                           if (item != null) {
                               HandleCourse(
                                   item.foodId.toString(),
                                   item.foodId.toString(),
                                   "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                                   item.foodName.toString(),
                                   item.servingDescription.toString(),
                                   true,
                                   item.calories.toString(),
                                   item.protein.toString(),
                                   item.carbohydrate.toString(),
                                   item.fat.toString(),
                                   navigateToDetail = {
                                       item.foodId?.let { it1 -> item.servingId?.let { it2 ->
                                           navigateToDetail(it1,
                                               it2
                                           )
                                       } }
                                   },
                                   onEat = {
                                       item.id?.let { it1 -> mealPlanViewModel.postEatenFood(it1) }
                                   },
                                   onUneat = {
                                      item.id?.let { it1 -> mealPlanViewModel.deleteEatenFood(it1) }
                                  },
                                   onDelete = {
                                      item.id?.let { it1 -> mealPlanViewModel.deleteFoodFromMealPlan(it1) }
                                  }
                               )
                           }
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                       }
                       Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                       judulPlan("Dinner", {
                           // TODO input on regenerate meal
                       })
                       Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                       for (item in data.mealPlan?.dinner!!) {
                           if (item != null) {
                               HandleCourse(
                                   item.foodId.toString(),
                                   item.foodId.toString(),
                                   "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                                   item.foodName.toString(),
                                   item.servingDescription.toString(),
                                   false,
                                   item.calories.toString(),
                                   item.protein.toString(),
                                   item.carbohydrate.toString(),
                                   item.fat.toString(),
                                   navigateToDetail = {
                                       item.foodId?.let { it1 -> item.servingId?.let { it2 ->
                                           navigateToDetail(it1,
                                               it2
                                           )
                                       } }
                                   },
                                   onEat = {
                                       item.id?.let { it1 -> mealPlanViewModel.postEatenFood(it1) }
                                   },
                                   onUneat = {
                                       item.id?.let { it1 -> mealPlanViewModel.deleteEatenFood(it1) }
                                   },
                                   onDelete = {
                                       item.id?.let { it1 -> mealPlanViewModel.deleteFoodFromMealPlan(it1) }
                                   }
                               )
                           }
                           Spacer(modifier = Modifier
                               .fillMaxWidth()
                               .height(5.dp))
                       }
                       for (item in data.eatenFood?.dinner!!) {
                           if (item != null) {
                               HandleCourse(
                                   item.foodId.toString(),
                                   item.foodId.toString(),
                                   "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                                   item.foodName.toString(),
                                   item.servingDescription.toString(),
                                   true,
                                   item.calories.toString(),
                                   item.protein.toString(),
                                   item.carbohydrate.toString(),
                                   item.fat.toString(),
                                   navigateToDetail = {
                                       item.foodId?.let { it1 -> item.servingId?.let { it2 ->
                                           navigateToDetail(it1,
                                               it2
                                           )
                                       } }
                                   },
                                   onEat = {
                                       item.id?.let { it1 -> mealPlanViewModel.postEatenFood(it1) }
                                   },
                                   onUneat = {
                                       item.id?.let { it1 -> mealPlanViewModel.deleteEatenFood(it1) }
                                   },
                                   onDelete = {
                                       item.id?.let { it1 -> mealPlanViewModel.deleteFoodFromMealPlan(it1) }
                                   }
                               )
                           }
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

@Composable
fun judulPlan(
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
