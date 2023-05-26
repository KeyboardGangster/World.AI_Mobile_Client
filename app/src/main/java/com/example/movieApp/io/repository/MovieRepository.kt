package com.example.movieApp.io.repository

import com.example.movieApp.io.db.MovieDao
import com.example.movieApp.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    suspend fun addMovie(movie: Movie) = movieDao.insert(movie)

    suspend fun updateMovie(movie: Movie) = movieDao.update(movie)

    suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)

    fun getAllMovies(): Flow<List<Movie>> = movieDao.getAll()

    fun getAllFavoriteMovies(): Flow<List<Movie>> = movieDao.getAllFaves()

    suspend fun getById(id: String): Movie = movieDao.getMovie(id)
}