package com.example.movieApp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieApp.screens.*
import com.example.movieApp.viewmodel.MainViewModel

@Composable
fun MainNavigation() {
    val mainViewModel: MainViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(DETAIL_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val movieID = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY)

            if (movieID != null)
                DetailScreen(navController = navController, mainViewModel = mainViewModel, movieID = movieID)
        }
        composable(
            route = Screen.Favorite.route
        ) {
            FavoriteScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(
            route = Screen.AddMovie.route
        ) {
            AddMovieScreen(navController = navController, mainViewModel = mainViewModel)
        }
    }
}