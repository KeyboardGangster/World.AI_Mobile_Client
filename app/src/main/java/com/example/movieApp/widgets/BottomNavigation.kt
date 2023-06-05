package com.example.movieApp.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieApp.navigation.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldBottomBar(navController: NavController, content: @Composable () -> Unit) {
    Scaffold(bottomBar = { BottomBar(navController = navController) }) {
        Box(modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
            .fillMaxSize()
        ){
            content()
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    BottomNavigation (
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
            .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)),
        elevation = 10.dp
    ) {
        BottomNavigationItem (
            icon = { Icon(imageVector = Icons.Rounded.Home, contentDescription = "Home") },
            selected = navController.currentDestination?.route == Screen.Home.route,
            onClick = {
                if (navController.currentDestination?.route != Screen.Home.route)
                {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            }
        )

        BottomNavigationItem (
            icon = { Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add") },
            selected = navController.currentDestination?.route == Screen.AddMovie.route,
            onClick = {
                if (navController.currentDestination?.route != Screen.AddMovie.route)
                {
                    navController.navigate(Screen.AddMovie.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            }
        )

        BottomNavigationItem (
            icon = { Icon(imageVector = Icons.Rounded.BookmarkBorder, contentDescription = "Saved") },
            selected = navController.currentDestination?.route == Screen.Favorite.route,
            onClick = {
                if (navController.currentDestination?.route != Screen.Favorite.route)
                {
                    navController.navigate(Screen.Favorite.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            }
        )
    }
}