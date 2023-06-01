package com.example.movieApp.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieApp.models.Tags
import com.example.movieApp.models.getTagColors
import com.example.movieApp.navigation.Screen
import com.example.movieApp.utils.InjectorUtils
import com.example.movieApp.viewmodel.HomeScreenViewModel
import com.example.movieApp.widgets.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeScreenViewModel = viewModel(
        factory = InjectorUtils.provideHomeScreenViewModelFactory(LocalContext.current))

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        val moviesState = viewModel.movieList.collectAsState()
        val coroutineScope = rememberCoroutineScope()

        ScaffoldBottomBar(navController = navController) {
            Column(modifier = Modifier.fillMaxSize()) {
                DisplayTags()
                ImageGallery()
            }
        }
    }
}

