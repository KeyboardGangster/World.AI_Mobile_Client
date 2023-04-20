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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun ShowImage(modifier: Modifier, url: String, isFav: Boolean, favForceUpdate: Boolean, favOnClick: () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
        elevation = 5.dp,
    ) {
        AsyncImage(model = url, contentDescription = null, contentScale = ContentScale.FillWidth)

        if (favForceUpdate)
            FavIconForcedUpdate(isFav = isFav, onClick = favOnClick)
        else
            FavIcon(isFav = isFav, onClick = favOnClick)
    }
}

@Composable
fun FavIcon(isFav: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "",
                Modifier.padding(
                    PaddingValues(Dp(10f))
                )
            )
        }
    }
}

@Composable
fun FavIconForcedUpdate(isFav: Boolean, onClick: () -> Unit) {
    var isFaveState by remember {
        mutableStateOf(isFav)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(onClick = {
            isFaveState = !isFaveState
            onClick()
        }) {
            Icon(
                imageVector = if (isFaveState) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "",
                Modifier.padding(
                    PaddingValues(Dp(10f))
                )
            )
        }
    }
}