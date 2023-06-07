package com.capstone.nutripal.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object MealPlan : Screen("meal-plan")
    object Intakes : Screen("intakes")
    object Profile : Screen("profile")
    object SearchPage : Screen("search-page")
    object DetailPage : Screen("home/{foodId}") {
        fun createRoute(foodId: String) = "home/$foodId"
    }
}
