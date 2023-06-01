package com.example.movieApp.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class World(
    @PrimaryKey val id: String,
    val prompt: String,
    val images: List<String>,
    var isFavorite: Boolean = false) {
}