package com.capstone.nutripal.ui.components.general

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.ui.theme.NutriPalTheme

@Composable
fun TableGizi (
    serving_description : String,
    calories: Int?,
    fat: Double?,
    saturated_fat: Double?,
    trans_fat: Double?,
    polyunsaturated_fat: Double?,
    monounsaturated_fat: Double?,
    cholesterol: Int?,
    carbohydrates: Double?,
    protein: Double?,
    sodium: Int?,
    potassium: Int?,
    fiber: Double?,
    sugar: Double?,
    added_sugars:  Double?,
    vitamin_d:  Double?,
    vitamin_a:  Int?,
    vitamin_c: Double?,
    calcium: Int?,
    iron: Double?,
    ) {
    Column(
        modifier = Modifier
            .border(1.dp, Color.Gray, RectangleShape)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SpaceBetweenItem(
                "Serving Size",
                serving_description
            )
            Divider(Modifier, Color.LightGray, 12.dp)
            RightOnlyItem(
                "Per portion"
            )
            Divider(Modifier, Color.LightGray, 6.dp)
            SpaceBetweenItem(
                "Calories",
                calories.toString()
            )
//            RightOnlyItem("48 kkal")
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Fat",
                "$fat g"
            )
            PaddedSpaceBetweenItem(
                "Saturated Fat",
                "$saturated_fat g"
            )
            PaddedSpaceBetweenItem(
                "Trans Fat",
                "$trans_fat g"
            )
            PaddedSpaceBetweenItem(
                "Polyunsaturated Fat",
                "$polyunsaturated_fat g"
            )
            PaddedSpaceBetweenItem(
                "Monounsaturated Fat",
                "$monounsaturated_fat g"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Cholesterol",
                "$cholesterol mg"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Protein",
                "$protein g"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Total Carbohydrates",
                "$carbohydrates g"
            )
            PaddedSpaceBetweenItem(
                "Fiber",
                "$fiber g"
            )
            PaddedSpaceBetweenItem(
                "Sugar",
                "$sugar g"
            )
            PaddedSpaceBetweenItem(
                "Added Sugar",
                "$added_sugars g"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Sodium",
                "$sodium mg"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Potassium",
                "$potassium mg"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Vitamin A",
                "$vitamin_a mcg"
            )
            SpaceBetweenItem(
                "Vitamin C",
                "$vitamin_c mcg"
            )
            SpaceBetweenItem(
                "Vitamin D",
                "$vitamin_d mcg"
            )
            SpaceBetweenItem(
                "Calcium",
                "$calcium mg"
            )
            SpaceBetweenItem(
                "Iron",
                "$iron mg"
            )
            Divider(Modifier, Color.LightGray, 6.dp)
        }
    }
}

@Composable
fun PaddedSpaceBetweenItem(
    left: String,
    right: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = left,
            style = MaterialTheme.typography.body1,
        )
        Text(
            text = right,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun SpaceBetweenItem(
    left: String,
    right: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = left,
            style = MaterialTheme.typography.body2,
        )
        Text(
            text = right,
            style = MaterialTheme.typography.body2,
        )
    }
}

@Composable
fun RightOnlyItem(
    right: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = right,
            style = MaterialTheme.typography.body2,
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun TableContentPreview() {
    NutriPalTheme {
//        TableGizi()
    }
}
