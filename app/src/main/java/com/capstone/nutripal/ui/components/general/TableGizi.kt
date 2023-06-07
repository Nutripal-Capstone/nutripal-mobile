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
import com.capstone.nutripal.ui.screen.detail.DetailContent
import com.capstone.nutripal.ui.theme.NutriPalTheme

@Composable
fun TableGizi (

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
                "Ukuran Porsi",
                "1 Nugget"
            )
            Divider(Modifier, Color.LightGray, 12.dp)
            RightOnlyItem(
                "Per porsi"
            )
            Divider(Modifier, Color.LightGray, 6.dp)
            SpaceBetweenItem(
                "Energi",
                "199 kj"
            )
            RightOnlyItem("48 kkal")
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Lemak",
                "3,01 g"
            )
            PaddedSpaceBetweenItem(
                "Lemak Jenuh",
                "0,644 g"
            )
            PaddedSpaceBetweenItem(
                "Lemak tak Jenuh Ganda",
                "0,851 g"
            )
            PaddedSpaceBetweenItem(
                "Lemak tak Jenuh Tunggal",
                "1,276 g"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Kolesterol",
                "9 mg"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Protein",
                "2,49 g"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Karbohidrat",
                "2,61 g"
            )
            PaddedSpaceBetweenItem(
                "Serat",
                "0,644 g"
            )
            PaddedSpaceBetweenItem(
                "Gula",
                "0,851 g"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Sodium",
                "2,61 g"
            )
            Divider(Modifier, Color.LightGray, 3.dp)
            SpaceBetweenItem(
                "Kalium",
                "2,61 g"
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
        TableGizi()
    }
}
