package com.example.movieApp.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.movieApp.R
import com.example.movieApp.io.FetchWorldWorker
import com.example.movieApp.io.db.WorldDatabase
import com.example.movieApp.models.Movie
import com.example.movieApp.io.repository.MovieRepository
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.models.World
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class AddMovieScreenViewModel(private val worldRepository: WorldRepository, private val workManager: WorkManager): ViewModel() {
    companion object {
        @Volatile
        var currentChanges: MutableState<List<String>>?=mutableStateOf(listOf())
    }

    init {
        if (currentChanges == null)
            currentChanges = mutableStateOf(listOf())
    }

    fun enqueueFetchRequest(prompt: String, key: String) {
        //input worker-params
        val fetchRequest = OneTimeWorkRequestBuilder<FetchWorldWorker>()
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(
                workDataOf(
                    "Prompt" to prompt,
                    "Key" to key
                )
            )
            .build()
        //schedule FetchWorldWorker
        workManager.beginUniqueWork(
            "fetch",
            ExistingWorkPolicy.KEEP,
            fetchRequest
        ).enqueue()
    }

    suspend fun saveWorld(prompt: String) {
        val cachedFilePaths = currentChanges?.value?: return
        val cachedBitmaps = worldRepository.loadImages(cachedFilePaths)
        val filePaths = worldRepository.saveImages(cachedBitmaps)
        worldRepository.deleteImages(cachedFilePaths)
        currentChanges?.value = listOf()

        val newWorld: World = World(
            id = Random.nextInt().toString(),
            prompt = prompt,
            images = filePaths
            //tagsItems.filter { it.isSelected }.map { Genre.valueOf(it.title) },
            //seed
        )

        worldRepository.add(newWorld)
    }

    suspend fun discardWorld() {
        val cachedFilePaths = currentChanges?.value?: return
        worldRepository.deleteImages(cachedFilePaths)
        currentChanges?.value = listOf()
    }

    fun loadSingleCachedImage(): Bitmap? {
        val cachedFilePaths = currentChanges?.value

        return if (cachedFilePaths.isNullOrEmpty()) null
        else worldRepository.loadImage(cachedFilePaths[0])
    }
}