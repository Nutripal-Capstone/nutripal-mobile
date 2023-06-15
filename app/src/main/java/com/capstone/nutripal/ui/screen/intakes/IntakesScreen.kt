package com.capstone.nutripal.ui.screen.intakes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.components.cards.DailyCardAnalysis
import com.capstone.nutripal.ui.components.cards.HistoryFoodCard
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun IntakesScreen(
    modifier: Modifier = Modifier,
    dataStore: StoreDataUser,
    intakesViewModel: IntakesViewModel,
) {
    println("TEST")
    val dataIntakes by intakesViewModel.result.collectAsState()
    val userToken = dataStore.getUserJwtToken().collectAsState(initial = "")
    println(dataIntakes)

    LaunchedEffect(key1 = true) {
        if (userToken.value != "") {
            intakesViewModel.getIntakes(userToken.value)
        }
    }

    NutriPalTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Intakes History",
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
                dataIntakes?.forEach { dataIntake ->
                    val nutritionGoal = dataIntake.nutritionGoal
                    val eatenFoodItem = dataIntake.eatenFood
                    val eatenNutrition = dataIntake.eatenNutrition
                    item {
                        Text(
                            dataIntake.date,
                            style = MaterialTheme.typography.body2
                        )
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp))
                        DailyCardAnalysis(
                            calorie = eatenNutrition.calories,
                            calorieNeeded = nutritionGoal.calorieGoal.toDouble(),
                            protein = eatenNutrition.protein,
                            proteinNeeded = nutritionGoal.proteinGoal.toDouble(),
                            carbs = eatenNutrition.carbohydrate,
                            carbsNeeded = nutritionGoal.carbohydrateGoal.toDouble(),
                            fat = eatenNutrition.fat,
                            fatNeeded = nutritionGoal.fatGoal.toDouble(),
                            isMealPlan = false
                        )
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp))
                        eatenFoodItem.forEach {eatenFood ->
                            HistoryFoodCard(
                                "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                                eatenFood.foodName,
                                eatenFood.servingDescription,
                                cal = eatenFood.calories,
                                pro = eatenFood.protein,
                                car = eatenFood.carbohydrate,
                                fat = eatenFood.fat
                            )
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp))
                        }
                    }
                }
            }
        }
    }
}
