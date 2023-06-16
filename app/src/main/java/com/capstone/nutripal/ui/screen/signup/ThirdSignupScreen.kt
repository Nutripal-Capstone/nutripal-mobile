package com.capstone.nutripal.ui.screen.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.screen.welcome.NutripalTopAppBar
import com.capstone.nutripal.ui.theme.IjoCompo
import com.capstone.nutripal.ui.theme.IjoTema
import com.capstone.nutripal.ui.theme.NutriPalTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ThirdSignupScreen (
    navController: NavController,
    signupViewModel: SignupViewModel,
) {
    val activityLevelOptions = listOf(
        ActivityLevelOption("Sedentary", "Little to no exercise"),
        ActivityLevelOption("Lightly Active", "Light exercise or sports 1-3 days a week"),
        ActivityLevelOption("Moderately Active", "Moderate exercise or sports 3-5 days a week"),
        ActivityLevelOption("Very Active", "Hard exercise or sports 6-7 days a week"),
        ActivityLevelOption("Super Active", "Very hard exercise or a physically demanding job"),
    )

    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { NutripalTopAppBar() }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(27.dp),
        ) {
            Text(
                text = "Activity Level",
                textAlign = TextAlign.Center,
                fontSize = 21.sp,
                fontWeight = FontWeight(700)
            )

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
            if (showError && selectedName.isEmpty()) {
                androidx.compose.material3.Text(
                    text = "Activity level is required",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    showError = true
                    if (selectedName.isNotEmpty()) {
                        navController.navigate(Screen.FourthSignup.route)
                        signupViewModel.saveThirdSignupSection(activityLevel = selectedName)
                    }
                },
                shape = RoundedCornerShape(27.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = IjoCompo,
                )
            ) {
                androidx.compose.material3.Text(
                    text = "Next",
                    fontWeight = FontWeight(700),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(59.dp))
        }
    }
}

data class ActivityLevelOption(
    val name: String,
    val description: String
)

@Composable
fun SelectableCourse(
    name : String,
    description : String,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {

    NutriPalTheme {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(
                    if (selected) IjoTema.copy(0.05f) else Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    2.dp,
                    if (selected) IjoCompo else Color.LightGray,
                    RoundedCornerShape(10.dp)
                )
        )
        {
            Column(
                modifier = modifier
                    .wrapContentHeight()
                    .padding(10.dp, 6.dp, 10.dp, 10.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}