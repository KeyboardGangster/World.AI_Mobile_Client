package com.example.movieApp.io

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

fun writeToImage(context: Context, bitmap: Bitmap): String {
    val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val filepath = dir!!.path + "/image" + Random.nextInt(0, 999999) + ".png"
    val file = File(filepath)
    file.createNewFile()
    val fOut = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
    fOut.flush()
    fOut.close()
    return filepath
}