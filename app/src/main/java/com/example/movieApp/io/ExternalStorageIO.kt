package com.example.movieApp.io

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.example.movieApp.R
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

class ExternalStorageIO(private val context: Context) {
    fun load(path: String): Bitmap {
        if (File(path).exists())
            return BitmapFactory.decodeFile(path)
        else
            return BitmapFactory.decodeResource(context.resources, R.drawable.avatar2)
    }

    suspend fun load(paths: List<String>): List<Bitmap> {
        val bitmaps: MutableList<Bitmap> = mutableListOf()

        for(path: String in paths) {
            if (!File(path).exists())
                continue

            val bmp = BitmapFactory.decodeFile(path)
            bitmaps.add(bmp)
        }

        return bitmaps
    }

    suspend fun delete(paths: List<String>) {
        for(path: String in paths) {
            File(path).delete()
        }
    }

    suspend fun save(bitmaps: List<Bitmap>): List<String> {
        val filesDir: String = context.filesDir.absolutePath
        val paths: MutableList<String> = mutableListOf()

        for(bmp: Bitmap in bitmaps) {
            paths.add(saveBmp(filesDir, bmp))
        }

        return paths
    }

    suspend fun cache(bitmaps: List<Bitmap>): List<String> {
        val cacheDir: String = context.cacheDir.absolutePath
        val paths: MutableList<String> = mutableListOf()

        for(bmp: Bitmap in bitmaps) {
            paths.add(saveBmp(cacheDir, bmp))
        }

        return paths.toList()
    }

    private fun saveBmp(dirPath: String, bitmap: Bitmap): String {
        val filepath = dirPath + "/image" + Random.nextInt(0, 999999) + ".jpeg"
        val file = File(filepath)
        file.createNewFile()
        val fOut = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
        fOut.flush()
        fOut.close()
        return filepath
    }
}
