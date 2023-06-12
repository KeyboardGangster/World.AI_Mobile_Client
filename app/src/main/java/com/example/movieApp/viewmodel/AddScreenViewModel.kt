package com.example.movieApp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.movieApp.io.FetchWorldWorker
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.models.World
import kotlin.random.Random

class AddScreenViewModel(private val worldRepository: WorldRepository, private val workManager: WorkManager): ViewModel() {
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
            ExistingWorkPolicy.REPLACE,
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
}