package com.example.movieApp.screens


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieApp.models.Movie
import com.example.movieApp.models.getMovies

@Composable
fun HomeScreen(navController: NavController) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        Surface {
            Column {
                TopAppBar(navController)
                MovieList(navController, getMovies())
            }
        }
    }
}


@Composable
fun MovieList(navController: NavController, movies: List<Movie>) {
    Column {
        LazyColumn {
            items(movies) { movie ->
                MovieRow(movie) { movieID ->
                    navController.navigate("detailscreen/$movieID")
                }
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit) {
    var expandInfo by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.clip(RoundedCornerShape(Dp(20f))),
        verticalArrangement = Arrangement.Top,
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onItemClick.invoke(movie.id) }) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = LoadImage(movie),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "",
                    Modifier.padding(
                        PaddingValues(Dp(10f))
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dp(5f)),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = movie.title, fontWeight = FontWeight.Bold
            )

            IconButton(onClick = {
                expandInfo = !expandInfo
            }) {
                Icon(
                    imageVector = if (expandInfo) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = ""
                )
            }
        }

        AnimatedVisibility(visible = expandInfo) {
            ShowMovieContents(movie = movie)
        }
    }
}

@Composable
fun LoadImage(movie: Movie): Painter {
    return rememberAsyncImagePainter(movie.images[0])
}

@Composable
fun ShowMovieContents(movie: Movie) {
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

@Composable
fun TopAppBar(navController: NavController) {
    var expand by remember {
        mutableStateOf(false)
    }

    TopAppBar(elevation = 4.dp, title = {
        Text("Movies")
    }, backgroundColor = MaterialTheme.colors.primarySurface, actions = {
        IconButton(onClick = { expand = !expand }) {
            Icon(Icons.Filled.MoreVert, null)
        }
        DropdownMenu(expanded = expand, onDismissRequest = {
            expand = false
        }) {
            DropdownMenuItem(onClick = {
                expand = false
                navController.navigate("favoritescreen")
            }) {
                Row {
                    Icon(Icons.Filled.Favorite, null)
                    Text("Favorites")
                }
            }
        }
    })
}
