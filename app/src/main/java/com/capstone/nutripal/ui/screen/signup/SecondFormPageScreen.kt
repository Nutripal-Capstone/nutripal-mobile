package com.capstone.nutripal.ui.screen.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondFormPageScreen()  {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    var activityLevel by remember { mutableStateOf("") }
    var mealsPerDay by remember { mutableStateOf("") }

    var goal by remember { mutableStateOf("") }
    val goalOptions = listOf("Lose Weight", "Maintain Weight", "Gain Weight")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(27.dp),
    ) {
        Text(
            text = "Enter your body information",
            textAlign = TextAlign.Center,
            fontSize = 21.sp,
            fontWeight = FontWeight(700)
        )
        Spacer(modifier = Modifier.height(11.dp))
        Text(
            text = "This data is needed for personalized plan",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight(400)
        )
        Spacer(modifier = Modifier.height(21.dp))
        TextField(
            value = height,
            onValueChange = {height = it},
            label = { Text("Height") },
            modifier = Modifier.fillMaxWidth(),
            colors = nutripalAppTextFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(11.dp))
        TextField(
            value = weight,
            onValueChange = {weight = it},
            label = { Text("Weight") },
            modifier = Modifier.fillMaxWidth(),
            colors = nutripalAppTextFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(11.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                readOnly = true,
                value = goal,
                onValueChange = { },
                label = { Text("Goal") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = nutripalAppTextFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                goalOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            goal = selectionOption
                            expanded = false
                        }
                    ){
                        Text(text = selectionOption)
                    }
                }
            }
        }
    }
}