package com.example.movieApp.io.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieApp.models.World
import kotlinx.coroutines.flow.Flow

@Dao
interface WorldDao {
    @Insert
    suspend fun insert(world: World)

    @Update
    suspend fun update(world: World)

    @Delete
    suspend fun delete(world: World)

    @Query("SELECT * from world")
    fun getAll(): Flow<List<World>>

    @Query("SELECT * from world where isFavorite = 1")
    fun getAllFaves(): Flow<List<World>>

    @Query("SELECT * from world where id like :pk")
    suspend fun getMovie(pk: String) : World
}