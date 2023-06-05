package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieApp.io.repository.MovieRepository
import com.example.movieApp.io.repository.WorldRepository

class DetailScreenViewModelFactory(private val repository: WorldRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            return DetailScreenViewModel(worldRepository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}