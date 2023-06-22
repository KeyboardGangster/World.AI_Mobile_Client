package com.example.movieApp.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieApp.models.Tags
import com.example.movieApp.navigation.Screen
import com.example.movieApp.utils.InjectorUtils
import com.example.movieApp.viewmodel.HomeScreenViewModel
import com.example.movieApp.widgets.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeScreenViewModel = viewModel(
        factory = InjectorUtils.provideHomeScreenViewModelFactory(LocalContext.current))
    val worlds = viewModel.worldList.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        ScaffoldBottomBar(navController = navController) {

            Column(modifier = Modifier.fillMaxSize()) {
                /*WorldList(
                    worlds = worlds.value,
                    favForceUpdate = false,
                    onItemClick = {},
                    onFavClick = {}
                )
                Button(onClick = {
                    viewModel.enqueueFetchRequest("Some prompt", "some key")
                }) {
                    Text(text = "Add new World!")
                }*/

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
                        worlds.value
                    else
                        worlds.value.filter {world ->
                            world.tags.any{selectedTags.contains(it)
                        }},
                    onFavClick = {
                        coroutineScope.launch {
                            viewModel.toggleFave(it)
                        }
                    },
                    onClick = {
                        navController.navigate(Screen.Detail.passId(it))
                    }
                )
            }
        }
    }
}

