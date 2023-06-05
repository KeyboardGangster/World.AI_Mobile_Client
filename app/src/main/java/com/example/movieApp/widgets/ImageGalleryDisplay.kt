package com.example.movieApp.widgets

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import com.example.movieApp.R
import com.example.movieApp.models.World

@Composable
fun ImageGallery(worlds: List<World>, onFavClick: (String) -> Unit, onClick: (String) -> Unit) {
    val rng = Random(seed = 0)

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
                worlds.forEach { world ->
                    DisplayWorld(
                        rng = rng,
                        world = world,
                        onFavClick = onFavClick,
                        onClick = onClick
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayWorld(rng: Random, world: World, onFavClick: (String) -> Unit, onClick: (String) -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(rng.nextInt(160, 300).dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Box (
            modifier = Modifier
                .fillMaxSize()
        ) {
            val bmp = BitmapFactory.decodeFile(world.images[0])
            Image (
                modifier = Modifier.fillMaxSize().clickable { onClick(world.id) },
                painter = BitmapPainter(bmp.asImageBitmap()),
                contentDescription = "images",
                alignment = Alignment.Center,
                contentScale = ContentScale.FillHeight,
            )

            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = {
                    onFavClick(world.id)
                }) {
                    Icon(
                        tint = Color.White,
                        imageVector = if (world.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmarked"
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayWorld(height: Int, world: World, onFavClick: (String) -> Unit, onClick: (String) -> Unit) {
    var isFaveState by remember {
        mutableStateOf(world.isFavorite)
    }

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(height.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Box (
            modifier = Modifier
                .fillMaxSize()
        ) {
            val bmp = BitmapFactory.decodeFile(world.images[0])
            Image (
                modifier = Modifier.fillMaxSize().clickable { onClick(world.id) },
                painter = BitmapPainter(bmp.asImageBitmap()),
                contentDescription = "images",
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth
            )

            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = {
                    onFavClick(world.id)
                    isFaveState = !isFaveState
                }) {
                    Icon(
                        tint = Color.White,
                        imageVector = if (isFaveState) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmarked"
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayPlaceholder(height: Int) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(height.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Box (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image (
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.avatar2),
                contentDescription = "images",
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth
            )
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