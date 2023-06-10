package com.capstone.nutripal.ui.screen.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nutripal.ui.theme.NutriPalTheme
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.capstone.nutripal.ui.theme.IjoCompo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen() {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    var gender by remember { mutableStateOf("") }
    val genderOptions = listOf("Male", "Female")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(27.dp),
    ) {
        Text(
            text = "Enter your personal information",
            textAlign = TextAlign.Center,
            fontSize = 21.sp,
            fontWeight = FontWeight(700)
        )
        Spacer(modifier = Modifier.height(11.dp))
        Text(
            text = "This data is needed for personal data",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight(400)
        )
        Spacer(modifier = Modifier.height(21.dp))
        TextField(
            value = name,
            onValueChange = {name = it},
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = nutripalAppTextFieldColors()
        )
        Spacer(modifier = Modifier.height(11.dp))
        TextField(
            value = age,
            onValueChange = {age = it},
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth(),
            colors = nutripalAppTextFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(11.dp))
//        TextField(
//            value = gender,
//            onValueChange = {gender = it},
//            label = { Text("Gender") }
//        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                readOnly = true,
                value = gender,
                onValueChange = { },
                label = { Text("Gender") },
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
                genderOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            gender = selectionOption
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

@Composable
fun nutripalAppTextFieldColors(
    labelColor: Color = IjoCompo,
    backgroundColor: Color = Color.White,
    indicatorColor: Color = IjoCompo,
    cursorColor: Color = IjoCompo,
) = TextFieldDefaults.colors(
    focusedLabelColor = labelColor,
    unfocusedLabelColor = labelColor,
    focusedContainerColor = backgroundColor,
    unfocusedContainerColor = backgroundColor,
    disabledContainerColor = backgroundColor,
    focusedIndicatorColor = indicatorColor,
    unfocusedIndicatorColor = indicatorColor,
    cursorColor = cursorColor
)

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    NutriPalTheme {
        SignupScreen()
    }
}