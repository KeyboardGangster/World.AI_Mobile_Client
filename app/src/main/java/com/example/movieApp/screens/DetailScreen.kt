package com.example.movieApp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.movieApp.viewmodel.DetailScreenViewModel
import com.example.movieApp.viewmodel.HomeScreenViewModel
import com.example.movieApp.widgets.MovieRow
import com.example.movieApp.widgets.ShowImages
import com.example.movieApp.widgets.SimpleAppBar
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(navController: NavController, viewModel: DetailScreenViewModel, movieID: String) {
    val movie = viewModel.getMovieFromId(movieID)
    val coroutineScope = rememberCoroutineScope()

    Column {
        SimpleAppBar(title = movie.title, navController = navController)
        MovieRow(
            movie = movie,
            favForceUpdate = true,
            onItemClick = {},
            onFavClick = {
                coroutineScope.launch {
                    viewModel.toggleFave(movie.id)
                }
            })
        ShowImages(urls = movie.images)
    }
}