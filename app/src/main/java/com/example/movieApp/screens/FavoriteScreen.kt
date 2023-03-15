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

@Composable
fun FavoriteScreen(navController: NavController) {
    val movies = getMovies()
    val someMovies = listOf(movies[2], movies[3], movies[4])

    Column {
        TopBarFavorite(navController)
        MovieRowFaves(movies = someMovies)
    }
}

@Composable
fun MovieRowFaves(movies: List<Movie>) {
    Column {
        LazyColumn {
            items(movies) { movie ->
                MovieRow(movie) {
                }
            }
        }
    }
}

@Composable
fun TopBarFavorite(navController: NavController) {
    TopAppBar(elevation = 4.dp, title = {
        Text("Favorites")
    }, backgroundColor = MaterialTheme.colors.primarySurface, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.KeyboardArrowLeft, "backIcon")
        }
    })
}
