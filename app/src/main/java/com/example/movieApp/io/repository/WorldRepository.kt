package com.example.movieApp.io.repository

import android.graphics.Bitmap
import com.example.movieApp.io.ExternalStorageIO
import com.example.movieApp.io.db.WorldDao
import com.example.movieApp.io.net.requestImages
import com.example.movieApp.models.World
import kotlinx.coroutines.flow.Flow

class WorldRepository(private val worldDao: WorldDao, private val storage: ExternalStorageIO) {
    suspend fun fetchImagesFromServer(prompt: String, key: String): Bitmap? = requestImages(prompt, key)

    suspend fun saveImagesToExternalStorage(bmp: Bitmap): String = storage.writeToImage(bmp)

    suspend fun add(world: World) = worldDao.insert(world)

    suspend fun update(world: World) = worldDao.update(world)

    suspend fun delete(world: World) = worldDao.delete(world)

    fun getAll(): Flow<List<World>> = worldDao.getAll()

    fun getAllFavorites(): Flow<List<World>> = worldDao.getAllFaves()

    suspend fun getById(id: String): World = worldDao.getMovie(id)
}