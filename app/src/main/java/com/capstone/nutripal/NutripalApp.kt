package com.capstone.nutripal

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.screen.SplashScreen
import com.capstone.nutripal.ui.screen.home.HomeScreen
import com.capstone.nutripal.ui.screen.signup.*
import com.capstone.nutripal.ui.screen.welcome.WelcomeScreen

@Composable
fun NutripalApp(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current as ComponentActivity
    val dataStore = StoreDataUser(context)
    val viewModelFactory = ViewModelFactory(dataStore)
    val signupViewModel: SignupViewModel = viewModel(factory = viewModelFactory)

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                navController = navController
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(name = "world", navController = navController)
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(Screen.Signup.route) {
            SignupScreen(navController = navController, signupViewModel = signupViewModel)
        }
        composable(Screen.SecondSignup.route) {
            SecondSignupScreen(navController = navController, signupViewModel = signupViewModel)
        }
        composable(Screen.ThirdSignup.route) {
            ThirdSignupScreen(navController = navController, signupViewModel = signupViewModel)
        }
        composable(Screen.FourthSignup.route) {
            FourthSignupScreen(navController = navController, signupViewModel = signupViewModel)
        }
    }
}