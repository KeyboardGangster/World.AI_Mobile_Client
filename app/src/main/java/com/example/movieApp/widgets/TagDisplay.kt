package com.example.movieApp.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.movieApp.models.Tags
import com.example.movieApp.models.getTagColors

@Composable
fun DisplayTags() {
    Row(modifier = Modifier.padding(15.dp, 30.dp)) {
        var width = 10.dp
        var height = 4.dp
        var horizontalPadding = 5.dp
        TagLabel(width, height, horizontalPadding)
        TagRow(width, height, horizontalPadding)
    }
}

@Composable
private fun TagLabel(width: Dp, height: Dp, horizontalPadding: Dp) {
    Box(modifier = Modifier
        .padding(0.dp, 0.dp, horizontalPadding, 0.dp)
        .border(1.dp, MaterialTheme.colors.onPrimary, MaterialTheme.shapes.large)
    ) {
        Text (
            text = "tags",
            modifier = Modifier.padding(width, height),
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
private fun TagRow(width: Dp, height: Dp, horizontalPadding: Dp) {
    val colors = getTagColors()
    LazyRow(
        modifier = Modifier
            .fadingEdge(leftRightFade)
            .fillMaxSize()
    ) {
        items(Tags.values().size) { index ->
            Box(modifier = Modifier
                .padding(horizontalPadding, 0.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(colors[index])
            ) {
                Text (
                    text = Tags.values()[index].name,
                    modifier = Modifier.padding(width, height),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}