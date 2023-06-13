package com.capstone.nutripal.ui.components.badges

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.ui.theme.IjoCompo
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White

@Composable
fun NutritionalChips(
    title: String,
    value: Double
) {

    var backgroundColor = White
    var contentColor = IjoCompo

    when(title) {
        "Cal" -> {
            contentColor = Color(0xFF00526C)
            backgroundColor = Color(0xFFFAFEFF)
        }
        "Car" -> {
            contentColor = Color(0xFF83400B)
            backgroundColor = Color(0xFFFFFDFB)
        }
        "Fat" -> {
            contentColor = Color(0xFF573C00)
            backgroundColor = Color(0xFFFFFFFF)
        }
        "Pro" -> {
            contentColor = Color(0xFF00420C)
            backgroundColor = Color(0xFFFBFFFC)
        }
    }

    NutriPalTheme() {
        Box(
            modifier = Modifier
                .wrapContentSize()
//                .width(61.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(backgroundColor)
                .border(1.dp, contentColor, RoundedCornerShape(7.dp))
        ) {
            Row(
                modifier = Modifier.padding(6.dp, 0.dp, 6.dp, 1.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "$title: $value",
                    color = contentColor,
                    style = MaterialTheme.typography.subtitle2,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NutritionalChipsPreview() {
    NutriPalTheme {
        Column() {
            NutritionalChips("Cal", 7000.0)
            NutritionalChips("Car", 7000.0)
            NutritionalChips("Pro", 7000.0)
            NutritionalChips("Fat", 7000.0)
        }
    }
}