package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieApp.repository.MovieRepository

class AddMovieScreenViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMovieScreenViewModel::class.java)) {
            return AddMovieScreenViewModel(movieRepository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}