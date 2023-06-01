package com.example.movieApp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.movieApp.R
import kotlin.random.Random

@Composable
fun ImageGallery() {
    val images = listOf(
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2,
        R.drawable.avatar2
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .fadingEdge(topBottomFade)
            .padding(0.dp, 0.dp, 0.dp, 50.dp)
    ) {
        Column (
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            CustomStaggeredVerticalGrid (
                numColumns = 2,
                modifier = Modifier.padding(5.dp)
            ) {
                images.forEach { img ->
                    Card (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(Random.nextInt(160, 300).dp),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Box (
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Image (
                                painter =  painterResource(id = img),
                                contentDescription = "images",
                                alignment = Alignment.Center,
                                contentScale = ContentScale.FillHeight
                            )

                            Box (
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                IconButton(onClick = {
                                    println("Test")
                                }) {
                                    Icon(
                                        tint = Color.White,
                                        imageVector = Icons.Default.BookmarkBorder,
                                        contentDescription = "Bookmarked"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomStaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurable, constraints ->
        val columnWidth = (constraints.maxWidth / numColumns)

        val itemConstraints = constraints.copy(maxWidth = columnWidth)

        val columnHeights = IntArray(numColumns) { 0 }

        val placeables = measurable.map { measurable ->
            val column = testColumn(columnHeights)
            val placeable = measurable.measure(itemConstraints)

            columnHeights[column] += placeable.height
            placeable
        }

        val height =
            columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
                ?: constraints.minHeight

        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val columnYPointers = IntArray(numColumns) { 0 }

            placeables.forEach { placeable ->
                val column = testColumn(columnYPointers)

                placeable.place(
                    x = columnWidth * column,
                    y = columnYPointers[column]
                )

                columnYPointers[column] += placeable.height
            }
        }
    }
}

private fun testColumn(columnHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE

    var columnIndex = 0

    columnHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            columnIndex = index
        }
    }
    return columnIndex
}