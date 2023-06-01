package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieApp.io.repository.MovieRepository

class DetailScreenViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            return DetailScreenViewModel(movieRepository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}