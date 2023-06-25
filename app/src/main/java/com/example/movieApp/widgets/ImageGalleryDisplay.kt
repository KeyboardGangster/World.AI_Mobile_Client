package com.example.movieApp.widgets

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.movieApp.R
import com.example.movieApp.models.World
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorldAllImages(height: Int, world: World, onFavClick: (String) -> Unit, onClick: (String) -> Unit) {
    var isFaveState by remember {
        mutableStateOf(world.isFavorite)
    }

    val pagerState = rememberPagerState()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while(true) {
            delay(3.seconds)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % world.images.size)
        }
    }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .height(height.dp),
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    pageCount = world.images.size
                ) { page ->
                    DisplaySavedImage(
                        modifier = Modifier.fillMaxSize(),
                        path = world.images[page],
                        alignment = Alignment.Center,
                        contentScale = ContentScale.FillWidth
                    )
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

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier
                .padding(10.dp)
                .height(20.dp),
            contentColor = MaterialTheme.colors.onPrimary,
        ) {
            world.images.forEachIndexed { index, _ ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.scrollToPage(index) } }
                )
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