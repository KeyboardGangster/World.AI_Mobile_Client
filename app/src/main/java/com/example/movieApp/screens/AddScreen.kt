package com.example.movieApp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieApp.models.ListItemSelectable
import com.example.movieApp.models.Tags
import com.example.movieApp.viewmodel.AddScreenViewModel
import com.example.movieApp.widgets.PreviewImage
import com.example.movieApp.widgets.ScaffoldBottomBar
import com.example.movieApp.widgets.SelectInput
import com.example.movieApp.widgets.TextInput
import kotlinx.coroutines.launch

@Composable
fun AddScreen(navController: NavController, viewModel: AddScreenViewModel) {
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                /*var key by rememberSaveable {
                    mutableStateOf("")
                }

                var prompt by rememberSaveable {
                    mutableStateOf("")
                }*/

                val tags = Tags.values().toList()

                var tagsItems by rememberSaveable {
                    mutableStateOf(tags.map { tag ->
                        ListItemSelectable(
                            reference = tag, isSelected = false
                        )
                    })
                }

                var isEnabledSaveButton by rememberSaveable {
                    mutableStateOf(false)
                }

                var isEnabledGenerateButton by rememberSaveable() {
                    mutableStateOf(false)
                }

                var isVisible by rememberSaveable {
                    mutableStateOf(false)
                }

                var isLoading by rememberSaveable {
                    mutableStateOf(false)
                }

                val responseData = AddScreenViewModel.currentChanges?.value

                val validKey = TextInput(
                    modifier = Modifier,
                    singleLine = true,
                    value = viewModel.key.value,
                    label = "Enter OpenAI-Key",
                    errorMsg = "Key must not be empty!",
                    predicate = { it.isNotEmpty() }) {
                    viewModel.key.value = it
                }

                Text(
                    text = "Create a world with a description:",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = { viewModel.fromSeed.value = false }) {
                        Text(text = "From prompt")
                    }
                    Button(onClick = { viewModel.fromSeed.value = true }) {
                        Text(text = "From seed")
                    }
                }

                val validPrompt = if (viewModel.fromSeed.value) TextInput(
                    modifier = Modifier,
                    singleLine = true,
                    value = viewModel.prompt.value,
                    label = "Enter seed. Example: 1DJ1AYYBRMS",
                    errorMsg = "Seed must not be empty!",
                    predicate = { it.isNotEmpty() },
                    onValueChange = { viewModel.prompt.value = it }
                )
                else TextInput(
                    modifier = Modifier.height(200.dp),
                    singleLine = false,
                    value = viewModel.prompt.value,
                    label = "Enter prompt. Example: A rainy forest with huge trees...",
                    errorMsg = "Prompt must not be empty!",
                    predicate = { it.isNotEmpty() },
                    onValueChange = { viewModel.prompt.value = it }
                )

                if (AddScreenViewModel.generationFailed?.value == null ||
                    AddScreenViewModel.generationFailed?.value == true
                ) {
                    Text(
                        text = "Generation Failed! Check your OpenAI API-key and prompt!",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }

                if (AddScreenViewModel.connectionFailed?.value == null ||
                    AddScreenViewModel.connectionFailed?.value == true
                ) {
                    Text(
                        text = "Connection to server failed. Try again later!",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }

                Text(
                    text = "Generate",
                    style = MaterialTheme.typography.h6
                )
                IconButton(enabled = isEnabledGenerateButton,
                    onClick = {
                        viewModel.enqueueFetchRequest()
                        isLoading = true
                    }) {
                    Icon(Icons.Rounded.Bolt, "")
                }
                isEnabledGenerateButton = validPrompt && validKey



                if (responseData != null && !responseData.imageFilePaths.isNullOrEmpty()) {
                    PreviewImage(height = 200, path = responseData.imageFilePaths[0])
                    isVisible = true
                    isLoading = false
                }

                var loading =
                    if (isLoading) {
                        Text(text = "The World is being generated and pictures are being taken, please wait!")
                    } else {
                        Text(text = "")
                    }

                if (isVisible) {

                    var worldName by rememberSaveable { mutableStateOf("")}

                    val validWorldName = TextField (value = worldName,
                        onValueChange = {worldName = it},
                        label = {Text("Pick a world name")})

                    val validTags = SelectInput(modifier = Modifier.fillMaxWidth(),
                        genreItems = tagsItems,
                        label = "Select tags",
                        errorMsg = "Select at least one tag!",
                        predicate = { tagsItems.any { it.isSelected } }) { item ->
                        tagsItems = tagsItems.map {
                            if (it.reference == item.reference) {
                                item.copy(isSelected = !item.isSelected)
                            } else {
                                it
                            }
                        }
                    }

                    isEnabledSaveButton = validPrompt && validKey && validTags &&
                            AddScreenViewModel.currentChanges != null &&
                            AddScreenViewModel.currentChanges?.value?.imageFilePaths?.isNotEmpty() ?: false

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 100.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(enabled = isEnabledSaveButton, onClick = {
                            coroutineScope.launch {
                                viewModel.saveWorld(tagsItems.filter { it.isSelected }
                                    .map { it.reference })
                            }
                        }) {
                            Text(text = "Save")
                        }
                        Button(enabled = isEnabledSaveButton, onClick = {
                            coroutineScope.launch {
                                viewModel.discardWorld()
                            }
                        }) {
                            Text(text = "Discard")
                        }
                    }
                }
            }
        }
    }
}
