package com.example.movieApp.io

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

class ExternalStorageIO(private val context: Context) {
    suspend fun writeToImage(bitmap: Bitmap): String {
        val dirPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
        val filepath = dirPath + "/image" + Random.nextInt(0, 999999) + ".png"
        val file = File(filepath)
        file.createNewFile()
        val fOut = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
        fOut.flush()
        fOut.close()
        return filepath
    }
}
