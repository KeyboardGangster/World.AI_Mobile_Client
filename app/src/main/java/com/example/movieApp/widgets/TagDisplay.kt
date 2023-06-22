package com.example.movieApp.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.movieApp.models.ListItemSelectable
import com.example.movieApp.models.Tags
import com.example.movieApp.models.getTagColors

@Composable
fun DisplayTags(tags: List<Tags>) = DisplayTags(
    tags = tags,
    selectedTags = tags.toHashSet(),
    onTagClick = {}
)

@Composable
fun DisplayTags(
    tags: List<Tags> = Tags.values().toList(),
    selectedTags: HashSet<Tags>,
    onTagClick: (Tags) -> Unit
) {
    Row(modifier = Modifier.padding(15.dp, 30.dp)) {
        var width = 10.dp
        var height = 4.dp
        var horizontalPadding = 5.dp
        TagLabel(width, height, horizontalPadding)
        TagRow(
            tags,
            if (selectedTags.isEmpty()) tags.toHashSet() else selectedTags,
            width,
            height,
            horizontalPadding,
            onTagClick
        )
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
private fun TagRow(
    tags: List<Tags>,
    selectedTags: HashSet<Tags>,
    width: Dp,
    height: Dp,
    horizontalPadding: Dp,
    onTagClick: (Tags) -> Unit
) {
    val colors = getTagColors()
    LazyRow(
        modifier = Modifier
            .fadingEdge(leftRightFade)
    ) {
        items(tags) { tag ->
            Box(modifier = Modifier
                .padding(horizontalPadding, 0.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(
                    if (selectedTags.contains(tag)) (colors[tag] ?: Color.Blue) else Color.Gray
                )
                .clickable { onTagClick.invoke(tag) }
            ) {
                Text (
                    text = tag.name,
                    modifier = Modifier.padding(width, height),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}