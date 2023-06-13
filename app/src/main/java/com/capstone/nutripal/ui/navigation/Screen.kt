package com.capstone.nutripal.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Welcome : Screen("welcome")
    object Signup : Screen("signup")
    object SecondSignup : Screen("signup2")
    object ThirdSignup : Screen("signup3")
    object FourthSignup : Screen("signup4")
    object MealPlan : Screen("meal-plan")
    object Intakes : Screen("intakes")
    object Profile : Screen("profile")
    object SearchPage : Screen("search-page")
    object DetailPage : Screen("home/{foodId}/{foodServingId}") {
        fun createRoute(foodId: String, foodServingId: String) = "home/$foodId/$foodServingId"
    }
}
