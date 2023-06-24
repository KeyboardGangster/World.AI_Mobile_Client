package com.example.movieApp.widgets

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.movieApp.R
import com.example.movieApp.models.World

@Composable
fun WorldGallery(worlds: List<World>, onFavClick: (String) -> Unit, onClick: (String) -> Unit) {
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
                    WorldSingleImage(
                        height = rng.nextInt(160, 300),
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
fun WorldSingleImage(height: Int, world: World, onFavClick: (String) -> Unit, onClick: (String) -> Unit) {
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
            DisplaySavedImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onClick(world.id) },
                path = world.images[0],
                alignment = Alignment.Center,
                contentScale = ContentScale.FillHeight
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
fun WorldAllImages(height: Int, world: World, onFavClick: (String) -> Unit, onClick: (String) -> Unit) {
    var isFaveState by remember {
        mutableStateOf(world.isFavorite)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(height.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyRow {
                items(world.images) {
                    DisplaySavedImage(
                        modifier = Modifier
                            .width(LocalConfiguration.current.screenWidthDp.dp)
                            .fillMaxSize()
                            .clickable { onClick(world.id) },
                        path = it,
                        alignment = Alignment.Center,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd
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
fun PreviewImage(height: Int, path: String) {
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
            DisplaySavedImage(
                modifier = Modifier.fillMaxSize(),
                path = path,
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth)
        }
    }
}

@Composable
fun DisplaySavedImage(modifier: Modifier, path: String, alignment: Alignment, contentScale: ContentScale) {
    var bmp: Bitmap? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = path) {
        bmp = BitmapFactory.decodeFile(path)
    }

    if (bmp == null) Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.placeholder),
        contentDescription = "loading...",
        alignment = alignment,
        contentScale = contentScale
    )
    else Image(
        modifier = modifier,
        painter = BitmapPainter(bmp!!.asImageBitmap()),
        contentDescription = "loaded image",
        alignment = alignment,
        contentScale = contentScale
    )
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

@Composable
fun WorldList(worlds: List<World>, favForceUpdate: Boolean, onItemClick: (String) -> Unit, onFavClick: (String) -> Unit) {
    LazyColumn {
        items(worlds) { world ->
            WorldEntry(
                world = world,
                favForceUpdate = favForceUpdate,
                onItemClick = { onItemClick.invoke(world.id) },
                onFavClick = { onFavClick.invoke(world.id) })
        }
    }
}

@Composable
fun WorldEntry(world: World, favForceUpdate: Boolean, onItemClick: () -> Unit, onFavClick: () -> Unit) {
    val bmp = BitmapFactory.decodeFile(world.images[0])
    Column(
        modifier = Modifier.clip(RoundedCornerShape(Dp(20f))),
        verticalArrangement = Arrangement.Top,
    ) {
        Image(
            painter= BitmapPainter(bmp.asImageBitmap()),
            contentDescription = null
        )
    }
}