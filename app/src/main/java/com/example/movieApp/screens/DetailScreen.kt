package com.example.movieApp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieApp.viewmodel.DetailScreenViewModel
import com.example.movieApp.widgets.DisplayTags
import com.example.movieApp.widgets.ScaffoldBottomBar
import com.example.movieApp.widgets.WorldAllImages
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(navController: NavController, viewModel: DetailScreenViewModel, id: String) {
    val world = viewModel.getWorldFromId(id)
    val coroutineScope = rememberCoroutineScope()

    ScaffoldBottomBar(navController = navController) {

        Column (modifier = Modifier.padding(10.dp)) {

            IconButton(onClick = {
            navController.navigateUp()
        }) {
            Icon(Icons.Rounded.ArrowBack, "")
        }
            WorldAllImages(
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
                fontSize = 30.sp,
                modifier = Modifier.padding(5.dp),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Rounded.Share, "Share this world")
            }
            DisplayTags()
            Text(text = "Some prompt that still needs to be stored in the DB, same with tags btw...", modifier = Modifier.padding(5.dp))
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))
            Text(text = "Seed: same as above. Isn't even implemented in World.AI yet...", modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun Detail() {

}