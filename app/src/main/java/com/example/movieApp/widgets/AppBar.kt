package com.example.movieApp.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieApp.navigation.Screen

@Composable
fun SimpleAppBar(title: String, navController: NavController) {
    TopAppBar(elevation = 4.dp, title = {
        Text(title)
    }, backgroundColor = MaterialTheme.colors.primarySurface, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.KeyboardArrowLeft, "backIcon")
        }
    })
}

@Composable
fun MainAppBar(title: String, navController: NavController) {
    var expand by remember {
        mutableStateOf(false)
    }

    TopAppBar(elevation = 4.dp, title = {
        Text(title)
    }, backgroundColor = MaterialTheme.colors.primarySurface, actions = {
        IconButton(onClick = { expand = !expand }) {
            Icon(Icons.Filled.MoreVert, null)
        }
        DropdownMenu(expanded = expand, onDismissRequest = {
            expand = false
        }) {
            DropdownMenuItem(onClick = {
                expand = false
                navController.navigate(Screen.Favorite.route)
            }) {
                Row {
                    Icon(Icons.Filled.Favorite, null)
                    Text("Favorites")
                }
            }
        }
    })
}