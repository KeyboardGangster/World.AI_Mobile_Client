package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.example.movieApp.io.repository.MovieRepository
import com.example.movieApp.io.repository.WorldRepository

class HomeScreenViewModelFactory(private val repository: WorldRepository, private val workManager: WorkManager): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(worldRepository = repository, workManager = workManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}