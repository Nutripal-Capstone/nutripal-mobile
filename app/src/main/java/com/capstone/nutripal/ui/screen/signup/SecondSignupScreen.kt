package com.capstone.nutripal.ui.screen.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.screen.welcome.NutripalTopAppBar
import com.capstone.nutripal.ui.theme.IjoCompo
import com.capstone.nutripal.ui.theme.White


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondSignupScreen(
    navController: NavController,
    signupViewModel: SignupViewModel
)  {
    var height by remember { mutableStateOf(signupViewModel.height.value) }
    var weight by remember { mutableStateOf(signupViewModel.weight.value) }

    var goal by remember { mutableStateOf(signupViewModel.goal.value) }
    val goalOptions = listOf("Lose Weight", "Maintain Weight", "Gain Weight")
    var expandedGoal by remember { mutableStateOf(false) }
    var goalHasFocus by remember { mutableStateOf(false) }
    var goalHasError by remember { mutableStateOf(false) }

    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { NutripalTopAppBar() }
    ) {
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
            RequiredTextField(
                value = height,
                onValueChange = {height = it},
                label = "Height",
                showError = showError,
                errorMessage = "Height is required",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                suffix = { Text("cm") }
            )
            Spacer(modifier = Modifier.height(11.dp))
            RequiredTextField(
                value = weight,
                onValueChange = {weight = it},
                label = "Weight",
                showError = showError,
                errorMessage = "Weight is required",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                suffix = { Text("kg") }
            )
            Spacer(modifier = Modifier.height(11.dp))
            ExposedDropdownMenuBox(
                expanded = expandedGoal,
                onExpandedChange = {
                    expandedGoal = !expandedGoal
                },
            ) {
                TextField(
                    readOnly = true,
                    value = goal,
                    onValueChange = { },
                    label = { Text("Goal") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandedGoal
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .onFocusChanged {
                            goalHasFocus = it.isFocused
                            if (!goalHasFocus) {
                                goalHasError = goal.isEmpty()
                            }
                        },
                    isError = showError and goalHasError,
                    colors = nutripalAppTextFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expandedGoal,
                    onDismissRequest = {
                        expandedGoal = false
                    },
                    modifier = Modifier.fillMaxWidth().background(White)
                ) {
                    goalOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                goal = selectionOption
                                expandedGoal = false
                                goalHasError = goal.isEmpty()
                            }
                        ){
                            Text(text = selectionOption)
                        }
                    }
                }
            }
            if (showError && goalHasError) {
                Text(
                    text = "Goal is required",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    showError = true
                    if (height.isNotEmpty() && weight.isNotEmpty()) {
                        signupViewModel.saveSecondSignupSection(height, weight, goal)
                        navController.navigate(Screen.ThirdSignup.route)
                    }
                },
                shape = RoundedCornerShape(27.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = IjoCompo,
                )
            ) {
                Text(
                    text = "Next",
                    fontWeight = FontWeight(700),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(59.dp))
        }
    }
}