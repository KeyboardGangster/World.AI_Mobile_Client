package com.example.movieApp.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieApp.models.Movie
import com.example.movieApp.models.getMovies

class MainViewModel: ViewModel() {
    private val _movieList = getMovies().toMutableStateList()

    val movieList: List<Movie>
        get() = _movieList
    val faveMovieIDs: List<Movie>
        get() = _movieList.filter { it.isFavorite.value }

    fun toggleFave(movieID: String) {
        val movie = _movieList.find { it.id ==  movieID} ?: return
        movie.isFavorite.value = !movie.isFavorite.value
    }

    fun addMovie(movie: Movie) {
        _movieList.add(movie)
    }

    /*fun toggleFave(movieID: String){
        val movie = _movieList.find { it.id ==  movieID} ?: return
        val index = _movieList.indexOf(movie)
        _movieList[index] = _movieList[index].copy(isFavorite = !movie.isFavorite)
    }*/
}