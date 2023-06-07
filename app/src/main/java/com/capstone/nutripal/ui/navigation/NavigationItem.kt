package com.capstone.nutripal.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val iconOutlined: ImageVector,
    val screen: Screen
)