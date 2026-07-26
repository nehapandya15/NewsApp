package com.example.newsapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.newsapp.R

sealed class BottomNavItem(
    val route: String,
    val titleRes: Int,
    val icon: ImageVector
) {

    data object Home : BottomNavItem(
        route = "home",
        titleRes = R.string.home,
        icon = Icons.Default.Home
    )

    data object Bookmarks : BottomNavItem(
        route = "bookmarks",
        titleRes = R.string.bookmarks,
        icon = Icons.Default.Bookmarks
    )
}