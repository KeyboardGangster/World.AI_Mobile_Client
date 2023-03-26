package com.example.movieApp.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieApp.models.Movie


@Composable
fun MovieList(movies: List<Movie>, onItemClick: (String) -> Unit) {
    LazyColumn {
        items(movies) { movie ->
            MovieRow(movie, onItemClick)
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier.clip(RoundedCornerShape(Dp(20f))),
        verticalArrangement = Arrangement.Top,
    ) {
        MovieHeader(movie, onItemClick)
        MovieBody(movie)
    }
}

@Composable
fun MovieHeader(movie: Movie, onItemClick: (String) -> Unit) {
    ShowImage(
        modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .clickable { onItemClick.invoke(movie.id) },
        url = movie.images[0],
        faveIcon = true)
}

@Composable
fun MovieBody(movie: Movie) {
    ExpandableInfo(movie = movie)
}

@Composable
fun ExpandableInfo(movie: Movie) {
    var expandInfo by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dp(5f)),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = movie.title, fontWeight = FontWeight.Bold)

        IconButton(onClick = {
            expandInfo = !expandInfo
        }) {
            Icon(
                imageVector = if (expandInfo) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = null
            )
        }
    }

    AnimatedVisibility(visible = expandInfo) {
        MovieContents(movie = movie)
    }
}

@Composable
fun MovieContents(movie: Movie) {
    Column {
        Text(text = "Director: " + movie.director)
        Text(text = "Released: " + movie.year)
        Text(text = "Genre: " + movie.genre)
        Text(text = "Actors: " + movie.actors)
        Text(text = "Rating: " + movie.rating)
        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.size(15.dp))
        Text(text = "Plot: " + movie.plot)
    }
}

