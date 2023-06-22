package com.example.movieApp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieApp.models.Tags
import com.example.movieApp.navigation.Screen
import com.example.movieApp.utils.InjectorUtils
import com.example.movieApp.viewmodel.FavoriteScreenViewModel
import com.example.movieApp.widgets.DisplayTags
import com.example.movieApp.widgets.WorldGallery
import com.example.movieApp.widgets.ScaffoldBottomBar
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(navController: NavController) {
    val viewModel: FavoriteScreenViewModel = viewModel(
        factory = InjectorUtils.provideFavoriteScreenViewModelFactory(LocalContext.current))
    val favesState = viewModel.worldListFaves.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var forceRecomposeHack: Boolean by remember {
        mutableStateOf(false)
    }
    ScaffoldBottomBar(navController = navController) {
        Column {
            val selectedTags = remember { mutableStateListOf<Tags>() }

            DisplayTags(selectedTags = selectedTags.toHashSet()) {
                if (selectedTags.contains(it))
                    selectedTags.remove(it)
                else
                    selectedTags.add(it)
            }
            WorldGallery(
                worlds =
                if (selectedTags.isEmpty())
                    favesState.value
                else
                    favesState.value.filter {world ->
                        world.tags.any{selectedTags.contains(it)
                    }},
                onFavClick = {
                    coroutineScope.launch {
                        viewModel.toggleFave(it)
                        forceRecomposeHack = !forceRecomposeHack
                    }
                },
                onClick = {
                    navController.navigate(Screen.Detail.passId(it))
                }
            )
        }
    }
}
