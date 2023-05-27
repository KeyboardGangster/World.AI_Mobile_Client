package com.example.movieApp.widgets

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.movieApp.models.Movie
import com.example.movieApp.models.World

@Composable
fun ImageGallery() {
    Text(text = "Hello There!")
    Row(Modifier.fillMaxWidth()) {
        RandomsizedImageList()
        RandomsizedImageList()
    }
}

@Composable
private fun RandomsizedImageList() {
    Column(modifier = Modifier.fillMaxWidth(0.5f)) {
        Text(text = "ewoijfwoiefwiedeewfewfwefwefweffwefwefwefwefwefwefwwefwefwefwefwefwewef", modifier = Modifier.fillMaxWidth())
    }
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