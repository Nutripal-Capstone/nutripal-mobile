package com.capstone.nutripal.ui.components.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import com.capstone.nutripal.R
import com.capstone.nutripal.ui.theme.*

@Composable
fun MealPlanNotFound(
    onClickRecommend: () -> Unit,
    onAddSomeFood: () -> Unit,

) {
NutriPalTheme() {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.notfoundmealplan),
            contentDescription = "Logo Nutripal",
            modifier = Modifier
                .size(180.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "You don't have any meal plan.",
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Find Food to Add",
            style = MaterialTheme.typography.body2,
            color = IjoCompo,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                onAddSomeFood()
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "or",
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.height(10.dp))
        androidx.compose.material3.Button(
            onClick = {
                onClickRecommend()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = IjoCompo,
            )
        ) {
            Text(
                text = "Recommend Meal For Today",
                style = MaterialTheme.typography.body2,
                color = White
            )
        }

    }
}

}


@Preview(showBackground = true)
@Composable
fun MealPlanNotFoundPrev() {
    NutriPalTheme {
        MealPlanNotFound({}, {})
    }
}
