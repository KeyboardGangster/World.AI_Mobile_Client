package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.example.movieApp.io.repository.WorldRepository

class AddScreenViewModelFactory(private val repository: WorldRepository, private val workManager: WorkManager): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddScreenViewModel::class.java)) {
            return AddScreenViewModel(worldRepository = repository, workManager = workManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}