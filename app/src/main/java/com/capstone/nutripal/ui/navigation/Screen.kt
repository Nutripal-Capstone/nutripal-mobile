package com.capstone.nutripal.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Welcome : Screen("welcome")
    object Signup : Screen("signup")
    object SecondSignup : Screen("signup2")
    object ThirdSignup : Screen("signup3")
    object FourthSignup : Screen("signup4")
}
