package com.capstone.nutripal.ui.screen.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.screen.welcome.NutripalTopAppBar
import com.capstone.nutripal.ui.theme.IjoCompo

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavHostController,
    signupViewModel: SignupViewModel
) {
    var name by remember { mutableStateOf(signupViewModel.name.value) }
    var age by remember { mutableStateOf(signupViewModel.age.value) }

    var gender by remember { mutableStateOf(signupViewModel.gender.value) }
    val genderOptions = listOf("Male", "Female")
    var expanded by remember { mutableStateOf(false) }

    var showError by remember { mutableStateOf(false) }

    var genderHasFocus by remember { mutableStateOf(false) }
    var genderHasError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { NutripalTopAppBar() }
    ) {
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
//            TextField(
//                value = name,
//                onValueChange = {name = it},
//                label = { Text("Name") },
//                modifier = Modifier.fillMaxWidth(),
//                colors = nutripalAppTextFieldColors(),
//            )
//            Spacer(modifier = Modifier.height(11.dp))
            RequiredTextField(
                value = name,
                onValueChange = {name = it},
                label = "Name",
                showError = showError,
                errorMessage = "Name is required"
            )
            Spacer(modifier = Modifier.height(11.dp))
//            TextField(
//                value = age,
//                onValueChange = {age = it},
//                label = { Text("Age") },
//                modifier = Modifier.fillMaxWidth(),
//                colors = nutripalAppTextFieldColors(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            )
            RequiredTextField(
                value = age,
                onValueChange = {age = it},
                label = "Age",
                showError = showError,
                errorMessage = "Age is required",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.height(11.dp))
//            ExposedDropdownMenuBox(
//                expanded = expanded,
//                onExpandedChange = {
//                    expanded = !expanded
//                },
//            ) {
//                TextField(
//                    readOnly = true,
//                    value = gender,
//                    onValueChange = { },
//                    label = { Text("Gender") },
//                    trailingIcon = {
//                        ExposedDropdownMenuDefaults.TrailingIcon(
//                            expanded = expanded
//                        )
//                    },
//                    modifier = Modifier
//                        .menuAnchor()
//                        .fillMaxWidth(),
//                    colors = nutripalAppTextFieldColors()
//                )
////                RequiredTextField(
////                    readOnly = true,
////                    value = gender,
////                    onValueChange = { },
////                    label = "Gender",
////                    trailingIcon = {
////                        ExposedDropdownMenuDefaults.TrailingIcon(
////                            expanded = expanded
////                        )
////                    },
////                    modifier = Modifier.menuAnchor(),
////                    showError = showError,
////                    errorMessage = "Gender is required",
////                )
//                ExposedDropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = {
//                        expanded = false
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    genderOptions.forEach { selectionOption ->
//                        DropdownMenuItem(
//                            onClick = {
//                                gender = selectionOption
//                                expanded = false
//                            }
//                        ){
//                            Text(text = selectionOption)
//                        }
//                    }
//                }
//            }

            // another exposedDropdownMenuBox
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
                        .fillMaxWidth()
                        .onFocusChanged {
                            genderHasFocus = it.isFocused
                            if (!genderHasFocus) {
                                genderHasError = gender.isEmpty()
                            }
                        },
                    isError = showError and genderHasError,
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
                                genderHasError = gender.isEmpty()
                            }
                        ){
                            Text(text = selectionOption)
                        }
                    }
                }
            }
            if (showError && genderHasError) {
                Text(
                    text = "Gender is required",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    showError = true
                    if (name.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty()) {
                        signupViewModel.saveFirstSignupSection(name, age, gender)
                        navController.navigate(Screen.SecondSignup.route)
                    }
                },
                shape = RoundedCornerShape(27.dp),
                modifier = Modifier.fillMaxWidth()
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

@Composable
fun RequiredTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    showError: Boolean,
    errorMessage: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    suffix: @Composable (() -> Unit) = {},
) {
    var hasFocus by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .onFocusChanged {
                hasFocus = it.isFocused
                if (!hasFocus) {
                    hasError = value.isEmpty()
                }
            },
        label = { Text(label) },
        singleLine = true,
        isError = showError and hasError,
        colors = nutripalAppTextFieldColors(),
        keyboardOptions = keyboardOptions,
        suffix = suffix,
    )
    if (showError && hasError) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(start = 4.dp)
        )
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
    errorContainerColor = backgroundColor,
    focusedIndicatorColor = indicatorColor,
    unfocusedIndicatorColor = indicatorColor,
    cursorColor = cursorColor,
)

//@Preview(showBackground = true)
//@Composable
//fun SplashScreenPreview() {
//    NutriPalTheme {
//        SignupScreen(rememberNavController())
//    }
//}