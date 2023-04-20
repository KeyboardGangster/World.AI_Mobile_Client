package com.example.movieApp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieApp.screens.*
import com.example.movieApp.utils.InjectorUtils
import com.example.movieApp.viewmodel.AddMovieScreenViewModel
import com.example.movieApp.viewmodel.DetailScreenViewModel
import com.example.movieApp.viewmodel.FavoriteScreenViewModel
import com.example.movieApp.viewmodel.HomeScreenViewModel

@Composable
fun MainNavigation() {
    //This is here so the viewModel can cache all the movies before opening detail-screen
    val detailScreenViewModel: DetailScreenViewModel = viewModel(
        factory = InjectorUtils.provideDetailScreenViewModelFactory(LocalContext.current))

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(DETAIL_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val movieID = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY)

            if (movieID != null)
                DetailScreen(navController = navController, viewModel = detailScreenViewModel, movieID = movieID)
        }
        composable(
            route = Screen.Favorite.route
        ) {
            FavoriteScreen(navController = navController)
        }
        composable(
            route = Screen.AddMovie.route
        ) {
            AddMovieScreen(navController = navController)
        }
    }
}