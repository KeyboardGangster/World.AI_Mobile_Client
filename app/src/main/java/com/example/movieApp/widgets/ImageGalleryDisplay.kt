package com.example.movieApp.widgets

import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ImageGallery() {
    Text(text = "Hello There!")
    Row(Modifier.fillMaxWidth()) {
        RandomsizedImageList()
        RandomsizedImageList()
    }
}

@Composable
private fun RandomsizedImageList() {
    Column(modifier = Modifier.fillMaxWidth(0.5f)) {
        Text(text = "ewoijfwoiefwiedeewfewfwefwefweffwefwefwefwefwefwefwwefwefwefwefwefwewef", modifier = Modifier.fillMaxWidth())
    }
}