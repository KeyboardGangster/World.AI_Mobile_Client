package com.example.movieApp.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.movieApp.handleExternalWrite
import com.example.movieApp.io.FetchWorldWorker
import com.example.movieApp.models.Movie
import com.example.movieApp.io.repository.MovieRepository
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.models.World
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeScreenViewModel(private val worldRepository: WorldRepository, private val workManager: WorkManager): ViewModel() {
    private val _worldList = MutableStateFlow(listOf<World>())
    val worldList: StateFlow<List<World>> = _worldList.asStateFlow()

    /*private val _movieList = MutableStateFlow(listOf<Movie>())
    val movieList: StateFlow<List<Movie>> =_movieList.asStateFlow()*/

    init {
        viewModelScope.launch {
            worldRepository.getAll().collect {worlds ->
                _worldList.value = worlds
            }
        }
    }

    fun enqueueFetchRequest(prompt: String, key: String) {
        //input worker-params
        val fetchRequest = OneTimeWorkRequestBuilder<FetchWorldWorker>()
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(workDataOf(
                "Prompt" to prompt,
                "Key" to key
            ))
            .build()
        //schedule FetchWorldWorker
        workManager.beginUniqueWork(
            "fetch",
            ExistingWorkPolicy.KEEP,
            fetchRequest
        ).enqueue()
    }

    suspend fun toggleFave(id: String) {
        val world: World = worldRepository.getById(id)
        world.isFavorite = !world.isFavorite
        worldRepository.update(world)
    }
}