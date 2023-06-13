package com.capstone.nutripal.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.ui.theme.IjoCompo
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialogs(
    title: String,
    subtitle:String,
    onAcceptClick : () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
        },
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(20.dp))
            .background(White),

    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp, 24.dp, 16.dp, 16.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        onAcceptClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IjoCompo,
                    )
                ) {
                    Text(
                        text = "Yes, add this food to the plan",
                        style = MaterialTheme.typography.body2,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                TextButton(
                    onClick = {
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.body2,
                        color = IjoCompo
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    NutriPalTheme {
//        Dialogs("Want to add this to your plan?", "Warning! This action may surpass the intended nutritional limit.")
    }
}