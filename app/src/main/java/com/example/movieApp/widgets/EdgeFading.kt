package com.example.movieApp.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.*

// Reference: https://stackoverflow.com/questions/66762472/how-to-add-fading-edge-effect-to-android-jetpack-compose-column-or-row
val leftRightFade = Brush.horizontalGradient(0f to Color.Transparent, 0.03f to Color.Red, 0.97f to Color.Red, 1f to Color.Transparent)
val topBottomFade = Brush.verticalGradient(0f to Color.Transparent, 0.01f to Color.Red, 0.99f to Color.Red, 1f to Color.Transparent)

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }