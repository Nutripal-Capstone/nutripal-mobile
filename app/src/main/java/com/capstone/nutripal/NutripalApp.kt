package com.capstone.nutripal

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.screen.SplashScreen
import com.capstone.nutripal.ui.screen.home.HomeScreen
import com.capstone.nutripal.ui.screen.signup.SignupScreen
import com.capstone.nutripal.ui.screen.welcome.WelcomeScreen

@Composable
fun NutripalApp(
    navController: NavHostController = rememberNavController(),
) {
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
            SignupScreen()
        }
    }
}