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
            val fromSeed = workerParameters.inputData.getBoolean("FromSeed", false)

            if (prompt == null || key == null)
                return Result.failure()

            val responseData = worldRepository.fetchFromServer(prompt, key, fromSeed)

            if (!responseData.success) {
                //failure
                AddScreenViewModel.generationFailed?.value = true
                return Result.success()
            }

            Log.d("Worker", "Got images!!!!")
            val cachedFilePaths = worldRepository.cacheImages(responseData.images)
            responseData.imageFilePaths = cachedFilePaths
            Log.d("Worker", "Cached images!!!!")
            AddScreenViewModel.currentChanges?.value = responseData
            Log.d("Worker", "Updated static property!!!!")
        }
        catch(exception: java.net.ConnectException) {
            AddScreenViewModel.connectionFailed?.value = true
        }
        catch(exception: Exception) {
            Log.d("Worker", exception.toString())
        }

        return Result.success()
    }
}