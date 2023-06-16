package com.capstone.nutripal.ui.screen.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.screen.welcome.NutripalTopAppBar
import com.capstone.nutripal.ui.theme.IjoCompo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FourthSignupScreen(
    navController: NavController,
    signupViewModel: SignupViewModel,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val resultMsg by signupViewModel.result.collectAsState()
    var showError by remember { mutableStateOf(false) }

    LaunchedEffect(resultMsg) {
        when (resultMsg) {
            "success" -> {
                navController.navigate(Screen.Home.route) {
                    popUpTo(0)
                }
            }
        }
    }

    val dietTypeOptions = listOf(
        DietTypeOption("Standard Balanced Diet", "Balanced macronutrient distribution for maintaining overall health"),
        DietTypeOption("High Carb Diet", "Favored by endurance athletes for sustained energy"),
        DietTypeOption("Keto Diet", "Low carb, high fat; used for rapid weight loss. Requires medical supervision due to drastic eating pattern shift"),
        DietTypeOption("High Protein Diet", "Ideal for bodybuilders to build muscle and recovery from intense workouts"),
        DietTypeOption("Low Fat Diet", "Beneficial for weight control and heart health"),
    )

    Scaffold(
        topBar = { NutripalTopAppBar() }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(27.dp),
        ) {
            Text(
                text = "Diet Type",
                textAlign = TextAlign.Center,
                fontSize = 21.sp,
                fontWeight = FontWeight(700)
            )

            var selectedName by rememberSaveable { mutableStateOf("") }
            for (option in dietTypeOptions) {
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
                    text = "Diet type is required",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    showError = true
                    if (selectedName.isNotEmpty()) {
                        val user = FirebaseAuth.getInstance().currentUser
                        user!!.getIdToken(true)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val idTokenSigned: String? = task.result.token
                                    if (idTokenSigned != null) {
                                        scope.launch {
                                            signupViewModel.signUp(idTokenSigned, dietType = selectedName)
                                        }
                                    }
                                } else {
                                    task.exception
                                }
                            }
                    }
                },
                shape = RoundedCornerShape(27.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = IjoCompo,
                )
            ) {
                Text(
                    text = "Signup",
                    fontWeight = FontWeight(700),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(59.dp))
        }
    }
}

data class DietTypeOption(
    val name: String,
    val description: String
)