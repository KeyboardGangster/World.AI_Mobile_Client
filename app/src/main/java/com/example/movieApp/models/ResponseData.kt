package com.example.movieApp.models

import android.graphics.Bitmap

data class ResponseData(
    val success: Boolean = false,
    val images: List<Bitmap> = mutableListOf(),
    val serverName: String = "",
    val eSeed: String = "",
    val timeOfDay: String = "",
    var imageFilePaths: List<String> = listOf()) {
}