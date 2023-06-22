package com.example.movieApp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
            DisplayTags(world.tags)
            Text(text = "Prompt: " + world.prompt, modifier = Modifier.padding(5.dp))
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))

            var expandInfo by remember {
                mutableStateOf(false)
            }

            IconButton(modifier = Modifier.align(Alignment.End),
                onClick = {
                expandInfo = !expandInfo
            }) {
                Icon(
                    imageVector = if (expandInfo) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = null
                )
            }

            AnimatedVisibility(visible = expandInfo) {
                Column {
                    Text(text = "Seed: " + world.eSeed, modifier = Modifier.padding(5.dp))
                    Text(text = "Time of day: " + world.timeOfDay, modifier = Modifier.padding(5.dp))
                    Text(text = "Source: " + world.sourceServer, modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}

@Composable
fun Detail() {

}