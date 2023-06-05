package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.example.movieApp.io.repository.MovieRepository
import com.example.movieApp.io.repository.WorldRepository

class AddMovieScreenViewModelFactory(private val repository: WorldRepository, private val workManager: WorkManager): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMovieScreenViewModel::class.java)) {
            return AddMovieScreenViewModel(worldRepository = repository, workManager = workManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}