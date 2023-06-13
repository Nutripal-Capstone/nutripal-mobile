package com.capstone.nutripal.ui.screen.mealplan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.ui.components.cards.DailyCardAnalysis
import com.capstone.nutripal.ui.components.cards.EatenCourse
import com.capstone.nutripal.ui.components.cards.MainCourse
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White


@Composable
fun MealPlan(
    modifier: Modifier = Modifier
) {
    MealPlanContent()
}

@Composable
fun MealPlanContent (
) {
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
               modifier = Modifier.fillMaxWidth()
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
                       Icon(
                           imageVector = Icons.Filled.Refresh,
                           contentDescription = "Food Search",
                       )
                   }
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
                       isMealPlan = true,
                   )
                   Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                   EatenCourse(
                       "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                       "Soto Ayam",
                       "1 portion",
                       700.0,
                       700.0,
                       700.0,
                       700.0,
                       true,
                   )

                   Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                   EatenCourse(
                       "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                       "Lodho Pisip",
                       "1 portion",
                       700.0,
                       700.0,
                       700.0,
                       700.0,
                       false,
                   )
                   Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                   EatenCourse(
                       "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                       "Warteg",
                       "1 portion",
                       700.0,
                       700.0,
                       700.0,
                       700.0,
                       false,
                   )
               }
           }
       }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    NutriPalTheme {
        MealPlanContent()
    }
}
