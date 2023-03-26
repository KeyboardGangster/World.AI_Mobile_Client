package com.example.movieApp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieApp.screens.*

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(DETAIL_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val movieID = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY)

            if (movieID != null)
                DetailScreen(navController, movieID = movieID)
        }
        composable(
            route = Screen.Favorite.route
        ) { FavoriteScreen(navController)}
    }
}