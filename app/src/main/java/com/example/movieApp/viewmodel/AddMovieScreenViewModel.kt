package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.movieApp.io.FetchWorldWorker
import com.example.movieApp.models.Movie
import com.example.movieApp.io.repository.MovieRepository
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.models.World
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddMovieScreenViewModel(private val worldRepository: WorldRepository, private val workManager: WorkManager): ViewModel() {
    suspend fun add(world: World) {
        worldRepository.add(world)
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
}