package com.example.movieApp.io

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movieApp.io.db.WorldDatabase
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.models.World
import com.example.movieApp.viewmodel.AddMovieScreenViewModel
import kotlin.random.Random

class FetchWorldWorker(private val context: Context, private val workerParameters: WorkerParameters):
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val worldRepository: WorldRepository = WorldRepository(
            WorldDatabase.getDatabase(context).worldDao(),
            ExternalStorageIO(context)
        )

        val prompt = workerParameters.inputData.getString("Prompt")
        val key = workerParameters.inputData.getString("Key")

        if (prompt == null || key == null)
            return Result.failure()

        val bmp = worldRepository.fetchImagesFromServer(prompt, key) ?: return Result.failure()
        val bitmaps = listOf(bmp)
        val cachedFilesPaths = worldRepository.cacheImages(bitmaps)
        AddMovieScreenViewModel.currentChanges?.value = cachedFilesPaths

        /*val filePath = worldRepository.saveImagesToExternalStorage(bmp)
        val world = World(
            id = Random.nextInt().toString(),
            prompt = prompt,
            images = listOf(filePath)
        )
        worldRepository.add(world)*/

        return Result.success()
    }
}