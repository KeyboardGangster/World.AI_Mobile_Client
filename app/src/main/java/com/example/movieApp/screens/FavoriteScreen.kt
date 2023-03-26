package com.example.movieApp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieApp.models.Movie
import com.example.movieApp.models.getMovies
import com.example.movieApp.widgets.MovieList
import com.example.movieApp.widgets.MovieRow
import com.example.movieApp.widgets.SimpleAppBar

@Composable
fun FavoriteScreen(navController: NavController) {
    val movies = getMovies()
    val someMovies = listOf(movies[2], movies[3], movies[4])

    Column {
        SimpleAppBar(title = "Favorites", navController = navController)
        MovieList(movies = someMovies) { }
    }
}
