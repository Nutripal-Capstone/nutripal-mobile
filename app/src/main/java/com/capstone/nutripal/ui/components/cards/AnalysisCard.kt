package com.capstone.nutripal.ui.components.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.R
import com.capstone.nutripal.ui.theme.BiruMuda
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.shadow

@Composable
fun HomeCardAnalysis(
    calorie: Int,
    calorieNeeded: Int,
    protein: Int,
    proteinNeeded: Int,
    carbs: Int,
    carbsNeeded: Int,
    fat: Int,
    fatNeeded: Int,
    modifier: Modifier = Modifier,
) {
    NutriPalTheme() {
//        Surface(
//            modifier = Modifier
//                .border(1.dp, shadow, RoundedCornerShape(10.dp)),
//        ) {
            Row(
                modifier = modifier.fillMaxWidth()
                    .background(BiruMuda, shape = RoundedCornerShape(12.dp)).padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    HomeCardItem("Calorie", calorie, calorieNeeded)
                    HomeCardItem("Carbohydrate", calorie, calorieNeeded)
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    HomeCardItem("Protein", calorie, calorieNeeded)
                    HomeCardItem("Fat", calorie, calorieNeeded)
                }
            }
//        }
    }
}

@Composable
fun HomeCardItem(
    title : String,
    now : Int,
    needed : Int,
    modifier: Modifier = Modifier,
) {
    val image : Int
    when(title) {
        "Calorie" -> {
            image = R.drawable.calorie
        }
        "Protein" -> {
            image = R.drawable.protein
        }
        "Fat" -> {
            image = R.drawable.fat
        }
        "Carbohydrate" -> {
            image = R.drawable.carb
        }
        else -> {
            image = R.drawable.calorie
        }
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement =  Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(image), // Replace R.drawable.my_image with your image resource
            contentDescription = null, // Provide a content description if needed for accessibility
            modifier = Modifier
                .size(40.dp)
        )
        Column(
            modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "$now",
                    modifier = Modifier,
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = "/$needed",
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
fun DailyCardAnalysis(
    calorie: Double,
    calorieNeeded: Double,
    protein: Double,
    proteinNeeded: Double,
    carbs: Double,
    carbsNeeded: Double,
    fat: Double,
    fatNeeded: Double,
    isMealPlan : Boolean,
    modifier: Modifier = Modifier,
) {
    NutriPalTheme() {
        Surface(
            modifier = Modifier
                .border(1.dp, shadow, RoundedCornerShape(10.dp)),
        ) {
            Row(
                modifier = modifier.fillMaxWidth().background(BiruMuda,  shape = RoundedCornerShape(10.dp)).padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DailyCardItem("Calorie", calorie, calorieNeeded, isMealPlan)
                Divider(
                    color = shadow,
                    modifier = Modifier
                        .height(44.dp)
                        .width(1.5.dp)
                )
                DailyCardItem("Protein", protein, proteinNeeded, isMealPlan)
                Divider(
                    color = shadow,
                    modifier = Modifier
                        .height(44.dp)
                        .width(1.5.dp)
                )
                DailyCardItem("Carbs", carbs, carbsNeeded, isMealPlan)
                Divider(
                    color = shadow,
                    modifier = Modifier
                        .height(44.dp)
                        .width(1.5.dp)
                )
                DailyCardItem("Fat", fat, fatNeeded, isMealPlan)
            }
        }
    }
}

@Composable
fun DailyCardItem(
    title: String,
    now: Double,
    needed: Double,
    isMealPlan: Boolean,
    modifier: Modifier = Modifier,
) {
    val image : Int
    val unit : String
    when(title) {
        "Calorie" -> {
            image = R.drawable.calorie
            unit = "g"
        }
        "Protein" -> {
            image = R.drawable.protein
            unit = "g"
        }
        "Fat" -> {
            image = R.drawable.fat
            unit = "g"
        }
        "Carbs" -> {
            image = R.drawable.carb
            unit = "g"
        }
        else -> {
            image = R.drawable.calorie
            unit = "g"
        }
    }
    Column(
        verticalArrangement =  Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement =  Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(image), // Replace R.drawable.my_image with your image resource
                contentDescription = null, // Provide a content description if needed for accessibility
                modifier = Modifier
                    .size(20.dp)
            )
            Text(
                text = title,
                modifier = Modifier,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "$now",
                modifier = Modifier,
                style = MaterialTheme.typography.body2
            )
            if (!isMealPlan) {
                Text(
                    text = "/$needed",
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeCardAnalysisPreview() {
    NutriPalTheme {
        Column() {
//            HomeCardAnalysis(1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000)
//            DailyCardAnalysis(1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000)
        }
    }
}