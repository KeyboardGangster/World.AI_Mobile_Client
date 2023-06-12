package com.example.movieApp.io

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movieApp.io.db.WorldDatabase
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.viewmodel.AddScreenViewModel

class FetchWorldWorker(private val context: Context, private val workerParameters: WorkerParameters):
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        try {
            Log.d("Worker", "RUNNNN!!!!")
            val worldRepository: WorldRepository = WorldRepository(
                WorldDatabase.getDatabase(context).worldDao(),
                ExternalStorageIO(context)
            )

            val prompt = workerParameters.inputData.getString("Prompt")
            val key = workerParameters.inputData.getString("Key")

            if (prompt == null || key == null)
                return Result.failure()

            val bitmaps = worldRepository.fetchImagesFromServer(prompt, key) ?: return Result.failure()
            Log.d("Worker", "Got images!!!!")
            val cachedFilesPaths = worldRepository.cacheImages(bitmaps)
            Log.d("Worker", "Cached images!!!!")
            AddScreenViewModel.currentChanges?.value = cachedFilesPaths
            Log.d("Worker", "Updated static property!!!!")
        }
        catch(exception: Exception) {
            Log.d("Worker", exception.toString())
        }


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