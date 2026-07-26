package com.example.newsapp.presentation.navigation

import android.net.Uri
import androidx.annotation.StringRes
import com.example.newsapp.R

sealed class NavRoutes(
    val route: String,
    @StringRes val title: Int,
    val showTopBar: Boolean = true,
    val showBottomBar: Boolean = true
) {

    data object Home : NavRoutes(
        "home",
        R.string.top_bar_title
    )

    data object Bookmarks : NavRoutes(
        "bookmarks",
        R.string.bookmarks
    )
    data object Detail : NavRoutes(
        "detail/{article}",
        R.string.article_details,
        showBottomBar = false
    )

}