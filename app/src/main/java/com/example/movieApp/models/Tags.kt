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

fun getTagColors() : Map<Tags, Color> {
    return mapOf<Tags, Color>(
        Tags.forest to Color(62, 95, 67),
        Tags.sunrise to Color(141, 70, 50),
        Tags.sun to Color(146, 129, 48),
        Tags.moon to Color(24, 40, 87),
        Tags.desert to Color(129, 115, 71),
        Tags.dry to Color(129, 76, 71),
        Tags.cold to Color(71, 129, 115)
    )
}