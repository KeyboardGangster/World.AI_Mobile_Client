package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieApp.models.Movie
import com.example.movieApp.io.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val _movieList = MutableStateFlow(listOf<Movie>())
    val movieList: StateFlow<List<Movie>> =_movieList.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getAllMovies().collect { movieList ->
                _movieList.value = movieList
            }
        }
    }

    suspend fun toggleFave(movieID: String) {
        val movie: Movie = movieRepository.getById(movieID)
        movie.isFavorite = !movie.isFavorite
        movieRepository.updateMovie(movie)
    }
}