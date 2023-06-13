package com.capstone.nutripal.ui.screen.intakes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.ui.components.cards.DailyCardAnalysis
import com.capstone.nutripal.ui.components.cards.HistoryFoodCard
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White


@Composable
fun Intakes(
    modifier: Modifier = Modifier,

) {

}

@Composable
fun IntakesContent(

) {
    NutriPalTheme() {
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
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
            ) {
                repeat(3) {
                    item() {
                        Text(
                            "Today",
                            style = MaterialTheme.typography.body2
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                        DailyCardAnalysis(
                            calorie = 100,
                            calorieNeeded = 1000,
                            protein = 100,
                            proteinNeeded = 1000,
                            carbs = 100,
                            carbsNeeded = 1000,
                            fat = 100,
                            fatNeeded = 1000,
                            isMealPlan = false
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                        repeat(3) {
                            HistoryFoodCard(

                            )
                            Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                        }
                    }
                }
            }
        }
    }
}