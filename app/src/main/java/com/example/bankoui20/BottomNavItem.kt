package com.example.bankoui20

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name : String,
    val route : String,
    val icon : Int,
    val badgeCount : Int = 0
)
