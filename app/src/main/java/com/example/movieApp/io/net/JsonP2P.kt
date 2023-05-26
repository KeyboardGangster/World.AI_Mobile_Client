package com.example.movieApp.io.net

import android.graphics.Bitmap
import android.os.Environment
import android.util.Base64
import androidx.lifecycle.viewModelScope
import com.example.movieApp.utils.byteArrayToBitmap
import com.example.movieApp.utils.byteArrayToInt32
import com.example.movieApp.utils.numberToByteArray
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import kotlin.random.Random

suspend fun requestImages(prompt: String, key: String): Bitmap? {
    val client = Socket("10.0.2.2", 9999)
    val inputStream = client.getInputStream()
    val outputStream = client.getOutputStream()

    val jsonReq = JSONObject()
    jsonReq.accumulate("Prompt", prompt)
    jsonReq.accumulate("Key", key)

    writeJSONString(outputStream, jsonReq.toString())
    val response: String = readJSONString(inputStream)
    val json: JSONObject = JSONObject(response)
    val imageAsString = json.getString("Image")
    var byteArray: ByteArray? = Base64.decode(imageAsString, Base64.DEFAULT)
    val bmp = if (byteArray == null) null else byteArrayToBitmap(byteArray)

    client.close()

    return bmp
}

fun readJSONString(inputStream: InputStream): String {
    val sizeInBytes = ByteArray(4)
    inputStream.read(sizeInBytes, 0, 4)
    val size = byteArrayToInt32(sizeInBytes)

    val stringInBytes = ByteArray(size)
    inputStream.read(stringInBytes, 0, size)
    return String(stringInBytes) //ASCII
}

fun writeJSONString(outputStream: OutputStream, toWrite: String) {
    outputStream.write(numberToByteArray(toWrite.length))
    outputStream.write(toWrite.toByteArray())
    outputStream.flush()
}