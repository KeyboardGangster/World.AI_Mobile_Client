package com.example.movieApp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieApp.models.Movie
import com.example.movieApp.models.getMovie
import com.example.movieApp.viewmodel.MainViewModel
import com.example.movieApp.widgets.MovieRow
import com.example.movieApp.widgets.ShowImages
import com.example.movieApp.widgets.SimpleAppBar

@Composable
fun DetailScreen(navController: NavController, mainViewModel: MainViewModel, movieID: String) {
    val movie = mainViewModel.movieList.find { it.id == movieID }?: return

    Column {
        SimpleAppBar(title = movie.title, navController = navController)
        MovieRow(
            movie = movie,
            onItemClick = {},
            onFavClick = { mainViewModel.toggleFave(movie.id) })
        ShowImages(urls = movie.images)
    }
}