package com.example.movieApp.screens

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieApp.models.Genre
import com.example.movieApp.models.ListItemSelectable
import com.example.movieApp.widgets.SimpleAppBar
import com.example.movieApp.models.World
import com.example.movieApp.utils.InjectorUtils
import com.example.movieApp.viewmodel.AddMovieScreenViewModel
import com.example.movieApp.widgets.DisplayPlaceholder
import com.example.movieApp.widgets.ScaffoldBottomBar
import com.example.movieApp.widgets.SelectInput
import com.example.movieApp.widgets.TextInput
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun AddMovieScreen(navController: NavController) {
    val viewModel: AddMovieScreenViewModel = viewModel(
        factory = InjectorUtils.provideAddMovieScreenViewModelFactory(LocalContext.current))
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    ScaffoldBottomBar(navController = navController) {
        Surface(
            modifier = Modifier
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

                var key by rememberSaveable {
                    mutableStateOf("")
                }

                var prompt by rememberSaveable {
                    mutableStateOf("")
                }

                val tags = Genre.values().toList()

                var tagsItems by rememberSaveable {
                    mutableStateOf(tags.map { genre ->
                        ListItemSelectable(
                            title = genre.toString(), isSelected = false
                        )
                    })
                }

                var isEnabledSaveButton by rememberSaveable {
                    mutableStateOf(false)
                }

                var isEnabledGenerateButton by rememberSaveable() {
                    mutableStateOf(false)
                }

                if (AddMovieScreenViewModel.currentChanges?.value.isNullOrEmpty())
                    DisplayPlaceholder(height = 200)
                else {
                    val bmp: Bitmap? = viewModel.loadSingleCachedImage()

                    if (bmp == null)
                        DisplayPlaceholder(height = 200)
                    else
                        DisplayPlaceholder(height = 200, bitmap = bmp)
                }
                Button(
                    enabled = isEnabledGenerateButton,
                    onClick = {
                        /*Fetch world from server and update this ui*/
                        viewModel.enqueueFetchRequest(prompt, key)
                    }
                ) {
                    Text("Generate")
                }

                val validKey = TextInput(
                    modifier = Modifier,
                    singleLine = true,
                    value = key,
                    label = "Enter OpenAI-Key",
                    errorMsg = "Key must not be empty!",
                    predicate = { it.isNotEmpty() }) {
                    key = it
                }

                val validPrompt = TextInput(
                    modifier = Modifier.height(200.dp),
                    singleLine = false,
                    value = prompt,
                    label = "Enter prompt.",
                    errorMsg = "Prompt must not be empty!",
                    predicate = { it.isNotEmpty() }) {
                    prompt = it
                }

                val validTags = SelectInput(modifier = Modifier,
                    genreItems = tagsItems,
                    label = "Select tags",
                    errorMsg = "Select at least one tag!",
                    predicate = { tagsItems.any { it.isSelected } }) { genreItem ->
                    tagsItems = tagsItems.map {
                        if (it.title == genreItem.title) {
                            genreItem.copy(isSelected = !genreItem.isSelected)
                        } else {
                            it
                        }
                    }
                }

                isEnabledGenerateButton = validPrompt && validKey
                isEnabledSaveButton = validPrompt && validKey && validTags &&
                        AddMovieScreenViewModel.currentChanges != null &&
                        AddMovieScreenViewModel.currentChanges?.value?.isNotEmpty()?: false

                Button(enabled = isEnabledSaveButton, onClick = {
                    coroutineScope.launch {
                        viewModel.saveWorld(prompt)
                    }
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}
