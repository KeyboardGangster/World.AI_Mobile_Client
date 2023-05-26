package com.example.movieApp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.TypeConverter
import com.example.movieApp.models.Genre
import java.nio.ByteBuffer


class CustomConverters {
    @TypeConverter
    fun stringListToString(value: List<String>) = value.joinToString(",")

    @TypeConverter
    fun stringToStringList(value: String) = value.split(",").map{ it.trim() }

    @TypeConverter
    fun enumListToString(value: List<Genre>) = value.joinToString(",") { it.name }

    @TypeConverter
    fun stringToEnumList(value: String) = value.split(",").map { Genre.valueOf(it.trim()) }
}

fun byteArrayToBitmap(bytes: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun byteArrayToInt32(bytes: ByteArray): Int {
    if (bytes.size != 4) {
        throw Exception("wrong length")
    }
    bytes.reverse()
    return ByteBuffer.wrap(bytes).int
}

fun numberToByteArray (data: Number, size: Int = 4) : ByteArray =
    ByteArray (size) {i -> (data.toLong() shr (i*8)).toByte()}