package com.example.movieApp.models

import androidx.compose.ui.graphics.Color

enum class Tags {
    forest,
    sunrise,
    sun,
    moon,
    desert,
    dry,
    cold
}

fun getTagColors() : Array<Color> {
    return arrayOf (
        Color(62, 95, 67),
        Color(146, 129, 48),
        Color(24, 40, 87),
        Color(141, 70, 50),
        Color(129, 115, 71),
        Color(129, 76, 71),
        Color(71, 129, 115)
    )
}