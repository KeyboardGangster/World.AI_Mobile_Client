package com.example.movieApp.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.movieApp.navigation.Screen
import com.example.movieApp.viewmodel.HomeScreenViewModel
import com.example.movieApp.widgets.MainAppBar
import com.example.movieApp.widgets.MovieList
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeScreenViewModel) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        val moviesState = viewModel.movieList.collectAsState()
        val coroutineScope = rememberCoroutineScope()

        Surface {
            Column {
                MainAppBar(title = "Movies", navController = navController)
                MovieList(
                    movies = moviesState.value,
                    favForceUpdate = false,
                    onItemClick = {
                        navController.navigate(Screen.Detail.passId(it))
                    },
                    onFavClick = {
                        coroutineScope.launch {
                            viewModel.toggleFave(it)
                        }
                    })
            }
        }
    }
}
