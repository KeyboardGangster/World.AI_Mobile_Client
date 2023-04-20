package com.example.movieApp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieApp.models.Genre
import com.example.movieApp.models.ListItemSelectable
import com.example.movieApp.widgets.SimpleAppBar
import com.example.movieApp.models.Movie
import com.example.movieApp.models.getMovies
import com.example.movieApp.viewmodel.AddMovieScreenViewModel
import com.example.movieApp.widgets.SelectInput
import com.example.movieApp.widgets.TextInput
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun AddMovieScreen(navController: NavController, viewModel: AddMovieScreenViewModel) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleAppBar("Add Movie", navController)
        },
    ) { padding ->
        MainContent(Modifier.padding(padding)) {
            coroutineScope.launch {
                viewModel.addMovie(it)
            }

            navController.popBackStack()
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, onAddClick: (Movie) -> Unit) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {


            var title by rememberSaveable {
                mutableStateOf("")
            }

            var year by rememberSaveable {
                mutableStateOf("")
            }

            val genres = Genre.values().toList()

            var genreItems by rememberSaveable {
                mutableStateOf(genres.map { genre ->
                    ListItemSelectable(
                        title = genre.toString(), isSelected = false
                    )
                })
            }

            var director by rememberSaveable {
                mutableStateOf("")
            }

            var actors by rememberSaveable {
                mutableStateOf("")
            }

            var plot by rememberSaveable {
                mutableStateOf("")
            }

            var rating by rememberSaveable {
                mutableStateOf("")
            }

            var isEnabledSaveButton by rememberSaveable {
                mutableStateOf(true)
            }

            val validTitle = TextInput(modifier = modifier,
                value = title,
                label = "Enter movie title.",
                errorMsg = "Title must not be empty!",
                predicate = { it.isNotEmpty() }) {
                title = it
            }

            val validYear = TextInput(modifier = modifier,
                value = year,
                label = "Enter movie year.",
                errorMsg = "Year must not be empty!",
                predicate = { it.isNotEmpty() }) {
                year = it
            }

            val validGenre = SelectInput(modifier = modifier,
                genreItems = genreItems,
                label = "Select genres",
                errorMsg = "Select at least one genre!",
                predicate = { genreItems.any { it.isSelected } }) { genreItem ->
                genreItems = genreItems.map {
                    if (it.title == genreItem.title) {
                        genreItem.copy(isSelected = !genreItem.isSelected)
                    } else {
                        it
                    }
                }
            }

            val validDirector = TextInput(modifier = modifier,
                value = director,
                label = "Enter director.",
                errorMsg = "Director must not be empty!",
                predicate = { it.isNotEmpty() }) {
                director = it
            }

            val validActors = TextInput(modifier = modifier,
                value = actors,
                label = "Enter actors.",
                errorMsg = "Actors must not be empty!",
                predicate = { it.isNotEmpty() }) {
                actors = it
            }

            TextInput(modifier = modifier.height(120.dp),
                value = plot,
                label = "Enter plot.",
                errorMsg = "",
                predicate = { true }) {
                plot = it
            }

            val validRating = TextInput(modifier = modifier,
                value = rating,
                label = "Enter rating",
                errorMsg = "Must be a number!",
                predicate = { it.isNotEmpty() && it.toFloatOrNull() != null }) {
                rating = if (it.startsWith("0")) {
                    ""
                } else {
                    it
                }
            }

            isEnabledSaveButton =
                validTitle && validYear && validGenre && validDirector && validActors && validRating

            Button(enabled = isEnabledSaveButton, onClick = {
                onAddClick.invoke(Movie(Random.nextInt().toString(),
                    title,
                    year,
                    genreItems.filter { it.isSelected }.map { Genre.valueOf(it.title) },
                    director,
                    actors,
                    plot,
                    listOf("https://demofree.sirv.com/nope-not-here.jpg"),
                    rating.toFloat()
                )
                )
            }) {
                Text(text = "Add")
            }
        }
    }
}
