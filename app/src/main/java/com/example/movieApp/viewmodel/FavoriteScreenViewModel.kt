package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieApp.models.Movie
import com.example.movieApp.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val _movieListFaves = MutableStateFlow(listOf<Movie>())
    val faveMovieList: StateFlow<List<Movie>> = _movieListFaves.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getAllFavoriteMovies().collect { movieList ->
                _movieListFaves.value = movieList
            }
        }
    }

    suspend fun toggleFave(movieID: String) {
        val movie: Movie = movieRepository.getById(movieID)
        movie.isFavorite = !movie.isFavorite
        movieRepository.updateMovie(movie)
    }
}