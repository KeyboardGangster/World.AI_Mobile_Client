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
import com.example.movieApp.models.ResponseData
import com.example.movieApp.models.Tags
import com.example.movieApp.models.World
import kotlin.random.Random

class AddScreenViewModel(private val worldRepository: WorldRepository, private val workManager: WorkManager): ViewModel() {
    companion object {
        @Volatile
        var currentChanges: MutableState<ResponseData>?=mutableStateOf(ResponseData())
        var generationFailed: MutableState<Boolean>?=mutableStateOf(false)
        var connectionFailed: MutableState<Boolean>?=mutableStateOf(false)
    }

    val key: MutableState<String> = mutableStateOf("")
    val prompt: MutableState<String> = mutableStateOf("")
    val fromSeed: MutableState<Boolean> = mutableStateOf(false)

    init {
        if (currentChanges == null)
            currentChanges = mutableStateOf(ResponseData())
        if (generationFailed == null)
            generationFailed = mutableStateOf(false)
        if (connectionFailed == null)
            connectionFailed = mutableStateOf(false)
    }

    fun enqueueFetchRequest() {
        currentChanges?.value = ResponseData()
        generationFailed?.value = false
        connectionFailed?.value = false
        //input worker-params
        val fetchRequest = OneTimeWorkRequestBuilder<FetchWorldWorker>()
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(
                workDataOf(
                    "Prompt" to this.prompt.value,
                    "Key" to this.key.value,
                    "FromSeed" to this.fromSeed.value
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

    suspend fun saveWorld(selectedTags: List<Tags>) {
        val responseData: ResponseData = currentChanges?.value?: return

        val cachedFilePaths = responseData.imageFilePaths
        //val cachedBitmaps = worldRepository.loadImages(cachedFilePaths)
        val filePaths = worldRepository.saveImages(responseData.images)
        worldRepository.deleteImages(cachedFilePaths)
        currentChanges?.value = ResponseData()
        generationFailed?.value = false
        connectionFailed?.value = false

        val newWorld: World = World(
            id = Random.nextInt().toString(),
            prompt = this.prompt.value,
            images = filePaths,
            tags = selectedTags,
            sourceServer = responseData.serverName,
            eSeed = responseData.eSeed,
            timeOfDay = responseData.timeOfDay
            //tagsItems.filter { it.isSelected }.map { Genre.valueOf(it.title) }
        )

        worldRepository.add(newWorld)
    }

    suspend fun discardWorld() {
        val responseData = currentChanges?.value?: return
        worldRepository.deleteImages(responseData.imageFilePaths)
        currentChanges?.value = ResponseData()
        generationFailed?.value = false
        connectionFailed?.value = false
    }
}