package com.example.movieApp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieApp.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * from movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * from movie where isFavorite = 1")
    fun getAllFaves(): Flow<List<Movie>>

    @Query("SELECT * from movie where id like :pk")
    suspend fun getMovie(pk: String) : Movie
}