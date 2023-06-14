package com.example.movieApp.screens

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieApp.models.ListItemSelectable
import com.example.movieApp.models.Tags
import com.example.movieApp.utils.InjectorUtils
import com.example.movieApp.viewmodel.AddScreenViewModel
import com.example.movieApp.widgets.PreviewImage
import com.example.movieApp.widgets.ScaffoldBottomBar
import com.example.movieApp.widgets.SelectInput
import com.example.movieApp.widgets.TextInput
import kotlinx.coroutines.launch

@Composable
fun AddScreen(navController: NavController) {
    val viewModel: AddScreenViewModel = viewModel(
        factory = InjectorUtils.provideAddScreenViewModelFactory(LocalContext.current))
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

                var key by rememberSaveable {
                    mutableStateOf("")
                }

                var prompt by rememberSaveable {
                    mutableStateOf("")
                }

                val tags = Tags.values().toList()

                var tagsItems by rememberSaveable {
                    mutableStateOf(tags.map { tag ->
                        ListItemSelectable(
                            title = tag.toString(), isSelected = false
                        )
                    })
                }

                var isEnabledSaveButton by rememberSaveable {
                    mutableStateOf(false)
                }

                var isEnabledGenerateButton by rememberSaveable() {
                    mutableStateOf(false)
                }

                val cachedFilePaths = AddScreenViewModel.currentChanges?.value
                if (!cachedFilePaths.isNullOrEmpty())
                    PreviewImage(height = 200, path = cachedFilePaths[0])


                val validKey = TextInput(
                    modifier = Modifier,
                    singleLine = true,
                    value = key,
                    label = "Enter OpenAI-Key",
                    errorMsg = "Key must not be empty!",
                    predicate = { it.isNotEmpty() }) {
                    key = it
                }

                Text(text = "Crate a world with a description:",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )

                val validPrompt = TextInput(
                    modifier = Modifier.height(200.dp),
                    singleLine = false,
                    value = prompt,
                    label = "Enter prompt. Example: A rainy forest with huge trees...",
                    errorMsg = "Prompt must not be empty!",
                    predicate = { it.isNotEmpty() }) {
                    prompt = it
                }

                Text(text = "Generate",
                    style = MaterialTheme.typography.h6
                    )
                IconButton( enabled = isEnabledGenerateButton,
                    onClick = {
                        viewModel.enqueueFetchRequest(prompt, key)
                    }) {
                    Icon(Icons.Rounded.Bolt, "")
                }

                val validTags = SelectInput(modifier = Modifier.fillMaxWidth(),
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
                        AddScreenViewModel.currentChanges != null &&
                        AddScreenViewModel.currentChanges?.value?.isNotEmpty()?: false

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
