package com.example.movieApp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    surface = Color(26, 26, 26),
    primary = Color(26, 26, 26),
    onPrimary = Color(235, 235, 235),
    primaryVariant = Purple700,
    secondary = Teal200
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    surface = Color(235, 235, 235),
    primary = Color(235, 235, 235),
    onPrimary = Color(40, 40, 40),
    primaryVariant = Purple700,
    secondary = Color(200, 200, 200)

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun Lab2Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}