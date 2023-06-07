package com.capstone.nutripal.ui.screen.mealplan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.ui.components.cards.DailyCardAnalysis
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
                           imageVector = Icons.Filled.Close,
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
                       fatNeeded = 1000
                   )
                   Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                   MainCourse(
                       "Breakfast",
                       "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
                       "Soto Ayam",
                       "1 portion",
                       true,
                       700.0,
                       700.0,
                       700.0,
                       700.0,
                   )
                   Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                   MainCourse(
                       "Lunch",
                       "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
                       "Lodho Pisip",
                       "1 portion",
                       false,
                       700.0,
                       700.0,
                       700.0,
                       700.0,
                   )
                   Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                   MainCourse(
                       "Dinner",
                       "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
                       "Warteg",
                       "1 portion",
                       false,
                       700.0,
                       700.0,
                       700.0,
                       700.0,
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
