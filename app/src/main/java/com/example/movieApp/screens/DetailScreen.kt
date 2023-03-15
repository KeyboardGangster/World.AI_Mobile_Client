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

@Composable
fun DetailScreen(navController: NavController, movieID: String) {
    val movie: Movie = getMovie(movieID)

    Column {
        TopBarDetail(navController, movie)
        MovieRow(movie = movie) {

        }
        ShowMovieImages(urls = movie.images)
    }
}

@Composable
fun TopBarDetail(navController: NavController, movie: Movie) {
    TopAppBar(elevation = 4.dp, title = {
        Text(movie.title)
    }, backgroundColor = MaterialTheme.colors.primarySurface, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.KeyboardArrowLeft, "backIcon")
        }
    })
}

@Composable
fun ShowMovieImages(urls: List<String>) {
    Column {
        Text("Movie Images", textAlign = TextAlign.Center, fontSize = 30.sp, modifier = Modifier.fillMaxWidth())
        LazyRow {items(urls) { url ->
            ShowImage(url)
            }
        }   
    }
}

@Composable
fun ShowImage(url: String) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
        elevation = 5.dp,
    ) {
        AsyncImage(model = url, contentDescription = null)
    }
}