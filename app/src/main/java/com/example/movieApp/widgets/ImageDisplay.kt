package com.example.movieApp.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun ShowImages(urls: List<String>) {
    Column {
        Text(
            "Movie Images",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            modifier = Modifier.fillMaxWidth()
        )
        LazyRow {
            items(urls) { url ->
                ShowImage(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp), url
                )
            }
        }
    }
}

@Composable
fun ShowImage(modifier: Modifier, url: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
        elevation = 5.dp,
    ) {
        AsyncImage(model = url, contentDescription = null, contentScale = ContentScale.FillWidth)
    }
}

@Composable
fun ShowImage(modifier: Modifier, url: String, isFav: MutableState<Boolean>, favOnClick: () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
        elevation = 5.dp,
    ) {
        AsyncImage(model = url, contentDescription = null, contentScale = ContentScale.FillWidth)
        FavIcon(isFav, favOnClick)
    }
}

@Composable
fun FavIcon(isFav: MutableState<Boolean>, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if (isFav.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "",
                Modifier.padding(
                    PaddingValues(Dp(10f))
                )
            )
        }
    }
}
