package com.capstone.nutripal

import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.outlined.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.capstone.nutripal.ui.navigation.NavigationItem
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.screen.detail.DetailScreen
import com.capstone.nutripal.ui.screen.home.HomeScreen
import com.capstone.nutripal.ui.screen.intakes.Intakes
import com.capstone.nutripal.ui.screen.mealplan.MealPlan
import com.capstone.nutripal.ui.screen.profile.ProfileScreen
import com.capstone.nutripal.ui.screen.search.SearchScreen
import com.capstone.nutripal.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun NutripalApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            var hasNavbars = mutableListOf(
                Screen.Home.route,
                Screen.MealPlan.route,
                Screen.Intakes.route,
                Screen.Profile.route,
            )
            if(currentRoute in hasNavbars) {
                BottomBar(navController)
            }

        },
        modifier = modifier
    ) { innerPadding ->
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = if (currentRoute == Screen.Home.route) IjoCompo else Color.Transparent,
                darkIcons = currentRoute != Screen.Home.route,
            )
        }
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { foodId ->
//                        navController.navigate(Screen.DetailPage.createRoute(foodId))
                    },
                    onSearchbarClicked = {
                        navController.navigate(Screen.SearchPage.route)
                    },
                    navigateToMealPlan = {
                        navController.navigate(Screen.MealPlan.route){
                            popUpTo("home") { inclusive = true }
                        }
                    },
                )
            }
            composable(Screen.MealPlan.route) {
                MealPlan()
            }
            composable(Screen.Intakes.route) {
                Intakes()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.SearchPage.route) {
                SearchScreen(
                    onBackClick = {
                        navController.navigateUp()
                    },
                    navigateToDetail = { foodId, foodServingId ->
                        navController.navigate(Screen.DetailPage.createRoute(foodId, foodServingId))
                    },
                )
            }
            composable(
                route = Screen.DetailPage.route,
                arguments = listOf(
                    navArgument("foodId") { type = NavType.LongType },
                    navArgument("foodServingId") { type = NavType.LongType }
                ),
            ) {
                val id = it.arguments?.getLong("foodId") ?: -1L
                val servingId = it.arguments?.getLong("foodServingId") ?: -1L
                DetailScreen(
                    foodId = id.toString(),
                    servingId = servingId.toString(),
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }

    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        modifier = modifier,
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                iconOutlined = Icons.Outlined.Home,
                screen = Screen.Home,
            ),
            NavigationItem(
                title = "Meal Plan",
                icon = Icons.Default.Fastfood,
                iconOutlined = Icons.Outlined.Fastfood,
                screen = Screen.MealPlan
            ),
            NavigationItem(
                title = "Intakes",
                icon = Icons.Default.FoodBank,
                iconOutlined = Icons.Outlined.FoodBank,
                screen = Screen.Intakes
            ),
            NavigationItem(
                title = "Profile",
                icon = Icons.Default.AccountCircle,
                iconOutlined = Icons.Outlined.AccountCircle,
                screen = Screen.Profile
            ),
        )
        BottomNavigation (
            modifier = modifier,
            backgroundColor = White,
        ){
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            println(currentRoute)
            navigationItems.map { item ->
                if(currentRoute == item.screen.route) {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = IjoTema
                            )
                        },
                        label = { Text(
                            text = item.title,
                            modifier = modifier,
                            color = defaultText
                        ) },
                        selected = true,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    )
                } else {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = item.iconOutlined,
                                contentDescription = item.title,
                                tint = disabledText
                            )
                        },
                        label = { Text(
                            text = item.title,
                            modifier = modifier,
                            color = disabledText
                        ) },
                        selected = true,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    )
                }

            }
        }
    }
}