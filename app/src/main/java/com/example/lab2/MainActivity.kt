package com.example.lab2

import android.graphics.Paint.Align
import android.graphics.drawable.Icon
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.lab2.ui.theme.Lab2Theme
import com.example.testapp.models.Movie
import com.example.testapp.models.getMovies

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab2Theme {
                // A surface container using the 'background' color from the theme
                Surface {
                    Column {
                        TopAppBar()
                        MovieList(getMovies())
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun MovieList(movies: List<Movie>) {
    Column {
        LazyColumn {
            items(movies) {movie ->
                MovieRow(movie)
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie) {
    var expandInfo by remember {
        mutableStateOf(false)
    }

    Column (modifier = Modifier
        .clip(RoundedCornerShape(Dp(20f))),
        verticalArrangement = Arrangement.Top,
    ) {
        Box (modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)) {
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
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dp(5f)),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = movie.title,
                fontWeight = FontWeight.Bold)

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
        
        /*if (expandInfo)
            ShowMovieContents(movie = movie)
        else
            Box {}*/
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
fun TopAppBar(){
    var expand by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        elevation = 4.dp,
        title = {
            Text("Movies")
        },
        backgroundColor = MaterialTheme.colors.primarySurface,
        actions = {
            IconButton(onClick = { expand = !expand }) {
                Icon(Icons.Filled.MoreVert, null)
            }
            DropdownMenu(
                expanded = expand,
                onDismissRequest = {
                    expand = false
                }
            ) {
                DropdownMenuItem(onClick = { expand = false }) {
                    Row {
                        Icon(Icons.Filled.Favorite, null)
                        Text("Favorites")
                    }
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Lab2Theme {
        Greeting("Android")
    }
}