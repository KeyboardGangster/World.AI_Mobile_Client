package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieApp.models.Movie
import com.example.movieApp.io.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddMovieScreenViewModel(private val movieRepository: MovieRepository): ViewModel() {
    suspend fun addMovie(movie: Movie) {
        movieRepository.addMovie(movie)
    }
}