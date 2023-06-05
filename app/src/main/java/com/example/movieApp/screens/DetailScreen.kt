package com.example.movieApp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieApp.viewmodel.DetailScreenViewModel
import com.example.movieApp.viewmodel.HomeScreenViewModel
import com.example.movieApp.widgets.BottomBar
import com.example.movieApp.widgets.DisplayTags
import com.example.movieApp.widgets.DisplayWorld
import com.example.movieApp.widgets.MovieRow
import com.example.movieApp.widgets.ScaffoldBottomBar
import com.example.movieApp.widgets.ShowImages
import com.example.movieApp.widgets.SimpleAppBar
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(navController: NavController, viewModel: DetailScreenViewModel, id: String) {
    val world = viewModel.getWorldFromId(id)
    val coroutineScope = rememberCoroutineScope()

    ScaffoldBottomBar(navController = navController) {
        Column {
            DisplayWorld(
                height = 200,
                world = world,
                onFavClick = {
                     coroutineScope.launch { viewModel.toggleFave(world.id) }
                },
                onClick = {

                }
            )
            Text(
                text = "World Name",
                fontSize = 30.sp
            )
            DisplayTags()
            Text(text = "Some prompt that still needs to be stored in the DB, same with tags btw...")
            Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
            Text(text = "Seed: same as above. Isn't even implemented in World.AI yet...")
            // SimpleAppBar(title = movie.title, navController = navController)
            /*MovieRow(
                movie = movie,
                favForceUpdate = true,
                onItemClick = {},
                onFavClick = {
                    coroutineScope.launch {
                        viewModel.toggleFave(movie.id)
                    }
                })
            ShowImages(urls = movie.images)*/
        }
    }
}

@Composable
fun Detail() {

}