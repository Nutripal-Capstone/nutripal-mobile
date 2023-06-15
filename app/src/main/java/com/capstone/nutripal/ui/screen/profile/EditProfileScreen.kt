package com.capstone.nutripal.ui.screen.profile

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.capstone.nutripal.R
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.ProfileData
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.components.general.shimmerEffect
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.screen.signup.*
import com.capstone.nutripal.ui.theme.IjoCompo
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White
import com.capstone.nutripal.ui.theme.secondText
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current as ComponentActivity,
    dataStore: StoreDataUser = StoreDataUser(context),
    profileViewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(dataStore, FakeFoodRepository())
    ),
    navController: NavController,
    ) {
    val userToken = dataStore.getUserJwtToken().collectAsState(initial = "")
    profileViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
//                EditProfileContent(ProfileData(), true, navController = navController)
                profileViewModel.viewModelScope.launch {
                    profileViewModel.getProfileDetail(userToken.value)
                }
            }
            is UiState.Success -> {
                val data = uiState.data
                EditProfileContent(data = data,  navController = navController)
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditProfileContent(
    data: ProfileData,
//    loading : Boolean,
//    modifier: Modifier = Modifier,
    navController: NavController,
) {
    var height by remember { mutableStateOf("1000 kg") }
    var weight by remember { mutableStateOf("1000 kg") }

    var goal by remember { mutableStateOf("Lose Weight") }
    val goalOptions = listOf("Lose Weight", "Maintain Weight", "Gain Weight")
    val dietTypeOptions = listOf(
        DietTypeOption("Standard Balanced Diet", "Balanced macronutrient distribution for maintaining overall health"),
        DietTypeOption("High Carb Diet", "Favored by endurance athletes for sustained energy"),
        DietTypeOption("Keto Diet", "Low carb, high fat; used for rapid weight loss. Requires medical supervision due to drastic eating pattern shift"),
        DietTypeOption("High Protein Diet", "Ideal for bodybuilders to build muscle and recovery from intense workouts"),
        DietTypeOption("Low Fat Diet", "Beneficial for weight control and heart health"),
    )

    val activityLevelOptions = listOf(
        ActivityLevelOption("Sedentary", "Little to no exercise"),
        ActivityLevelOption("Lightly Active", "Light exercise or sports 1-3 days a week"),
        ActivityLevelOption("Moderately Active", "Moderate exercise or sports 3-5 days a week"),
        ActivityLevelOption("Very Active", "Hard exercise or sports 6-7 days a week"),
        ActivityLevelOption("Super Active", "Very hard exercise or a physically demanding job"),
    )

    var expandedGoal by remember { mutableStateOf(false) }
    var goalHasFocus by remember { mutableStateOf(false) }
    var goalHasError by remember { mutableStateOf(false) }

    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Edit Profile",
                        style = MaterialTheme.typography.h1
                    )
                },
                backgroundColor = White,
                elevation = 1.dp,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) {

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                androidx.compose.material3.Text(
                    text = "Body information",
                    style = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.height(11.dp))
                RequiredTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = "Name",
                    showError = showError,
                    errorMessage = "Name is required",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                Spacer(modifier = Modifier.height(11.dp))
                RequiredTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = "Age",
                    showError = showError,
                    errorMessage = "Age is required",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                Spacer(modifier = Modifier.height(11.dp))
                RequiredTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = "Height",
                    showError = showError,
                    errorMessage = "Height is required",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    suffix = { androidx.compose.material3.Text("cm") }
                )
                Spacer(modifier = Modifier.height(11.dp))
                RequiredTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = "Weight",
                    showError = showError,
                    errorMessage = "Weight is required",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    suffix = { androidx.compose.material3.Text("kg") }
                )
                Spacer(modifier = Modifier.height(11.dp))
                androidx.compose.material3.ExposedDropdownMenuBox(
                    expanded = expandedGoal,
                    onExpandedChange = {
                        expandedGoal = !expandedGoal
                    },
                ) {
                    androidx.compose.material3.TextField(
                        readOnly = true,
                        value = goal,
                        onValueChange = { },
                        label = { androidx.compose.material3.Text("Goal") },
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
                            ) {
                                androidx.compose.material3.Text(text = selectionOption)
                            }
                        }
                    }
                }
                if (showError && goalHasError) {
                    androidx.compose.material3.Text(
                        text = "Goal is required",
                        color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(21.dp))
                Text(
                    text = "Diet Type",
                    style = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.height(5.dp))
                var selectedActivity by rememberSaveable { mutableStateOf("") }
                for (option in dietTypeOptions) {
                    SelectableCourse(
                        name = option.name,
                        description = option.description,
                        selected = selectedActivity == option.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedActivity = option.name
                            }
                    )
                    Spacer(modifier = Modifier.height(11.dp))
                }
                if (showError && selectedActivity.isEmpty()) {
                    androidx.compose.material3.Text(
                        text = "Diet type is required",
                        color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Activity Level",
                    style = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.height(5.dp))
                var selectedName by rememberSaveable { mutableStateOf("") }
                for (option in activityLevelOptions) {
                    SelectableCourse(
                        name = option.name,
                        description = option.description,
                        selected = selectedName == option.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedName = option.name
                            }
                    )
                    Spacer(modifier = Modifier.height(11.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                androidx.compose.material3.Button(
                    onClick = {
                        showError = true
//                    if (height.isNotEmpty() && weight.isNotEmpty()) {
//                        signupViewModel.saveSecondSignupSection(height, weight, goal)
//                        navController.navigate(Screen.ThirdSignup.route)
//                    }
                    },
                    shape = RoundedCornerShape(27.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = IjoCompo,
                    )
                ) {
                    androidx.compose.material3.Text(
                        text = "Save",
                        fontWeight = FontWeight(700),
                        color = Color.White
                    )
                }
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    NutriPalTheme {
//        EditProfileContent()
    }
}