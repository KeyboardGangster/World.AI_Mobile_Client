package com.example.movieApp.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieApp.io.CustomFileProvider
import com.example.movieApp.io.ExternalStorageIO
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.models.World
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class DetailScreenViewModel(private val worldRepository: WorldRepository): ViewModel() {
    private val _worldList = MutableStateFlow(listOf<World>())
    private val worldList: StateFlow<List<World>> =_worldList.asStateFlow()

    init {
        viewModelScope.launch {
            worldRepository.getAll().collect { worldList ->
                if (!worldList.isNullOrEmpty())
                    _worldList.value = worldList
            }
        }
    }

    suspend fun toggleFave(id: String) {
        val world: World = worldRepository.getById(id)
        world.isFavorite = !world.isFavorite
        worldRepository.update(world)
    }

    fun getWorldFromId(id: String): World {
        return worldList.value.first {
            it.id == id
        }
    }

    suspend fun removeWorld(world: World) {
        worldRepository.delete(world)
    }

    fun shareImage(localContext: Context, imagePaths: List<String>) {

        val imageUris = imagePaths.map {
            FileProvider.getUriForFile(
                localContext,
                "com.example.movieApp.fileProvider",
                File(it)
            )
        }

        val uris: ArrayList<Uri> = ArrayList(imageUris)

        val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
        shareIntent.setType("image/jpg")
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        localContext.startActivity(shareIntent)
        //localContext.startActivity(Intent.createChooser(shareIntent, "share image")) SecurityException??
    }
}