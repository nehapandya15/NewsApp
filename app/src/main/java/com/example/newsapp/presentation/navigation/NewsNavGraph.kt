package com.example.newsapp.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.bookmark.BookmarksScreen
import com.example.newsapp.presentation.details.DetailScreen
import com.example.newsapp.presentation.home.HomeScreen
import com.squareup.moshi.Moshi

@Composable
fun NewsNavGraph() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentScreen = when {
        currentRoute == NavRoutes.Home.route -> NavRoutes.Home
        currentRoute == NavRoutes.Bookmarks.route -> NavRoutes.Bookmarks
        currentRoute?.startsWith(NavRoutes.Detail.route) == true -> NavRoutes.Detail
        else -> NavRoutes.Home
    }
    Scaffold(

        topBar = {

            if (currentScreen.showTopBar) {

                NewsTopBar(
                    title = stringResource(currentScreen.title),
                    showBack = currentScreen == NavRoutes.Detail,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )

            }

        },

        bottomBar = {

            if (currentScreen.showBottomBar) {
                NewsBottomBar(navController)
            }

        }

    ) { padding ->

        NavHost(

            modifier = Modifier.padding(padding),

            navController = navController,

            startDestination = BottomNavItem.Home.route

        ) {

            composable(
                route = BottomNavItem.Home.route
            ) {

                HomeScreen(
                    onArticleClick = { article ->
                        val moshi = Moshi.Builder().build()

                        val json = moshi
                            .adapter(Article::class.java)
                            .toJson(article)

                        navController.navigate(
                            "detail/${Uri.encode(json)}"
                        )
                    }
                )

            }
            composable(NavRoutes.Bookmarks.route) {

                BookmarksScreen(
                    onArticleClick = { article ->
                        val moshi = Moshi.Builder().build()

                        val json = moshi
                            .adapter(Article::class.java)
                            .toJson(article)

                        navController.navigate(
                            "detail/${Uri.encode(json)}"
                        )
                    }
                )

            }
            composable(
                route = NavRoutes.Detail.route
            ) { backStackEntry ->

                val json = backStackEntry.arguments
                    ?.getString("article")
                    ?.let(Uri::decode)
                    .orEmpty()

                val article = Moshi.Builder()
                    .build()
                    .adapter(Article::class.java)
                    .fromJson(json)

                article?.let {
                    DetailScreen(
                        article = it
                    )
                }
            }

        }
    }
}
