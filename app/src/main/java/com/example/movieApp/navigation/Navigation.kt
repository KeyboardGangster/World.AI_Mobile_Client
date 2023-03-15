package com.example.movieApp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieApp.screens.DetailScreen
import com.example.movieApp.screens.FavoriteScreen
import com.example.movieApp.screens.HomeScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "homescreen") {
        composable("homescreen") { HomeScreen(navController) }
        composable("detailscreen/{movieID}") { backStackEntry ->
            val movieID = backStackEntry.arguments?.getString("movieID")

            if (movieID != null)
                DetailScreen(navController, movieID = movieID)
        }
        composable("favoritescreen") { FavoriteScreen(navController)}
    }
}