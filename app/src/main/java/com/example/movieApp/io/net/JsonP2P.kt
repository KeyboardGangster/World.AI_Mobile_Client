package com.example.movieApp.io.net

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.example.movieApp.utils.byteArrayToBitmap
import com.example.movieApp.utils.byteArrayToInt32
import com.example.movieApp.utils.numberToByteArray
import org.json.JSONObject
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

suspend fun requestImages(prompt: String, key: String): Bitmap? {
    val client = Socket("10.0.2.2", 9999)
    val inputStream = client.getInputStream().buffered()
    val outputStream = client.getOutputStream().buffered()

    val jsonReq = JSONObject()
    jsonReq.accumulate("Prompt", prompt)
    jsonReq.accumulate("Key", key)

    writeJSONString(outputStream, jsonReq.toString())
    val response: String = readJSONString(inputStream)
    //Log.d("Response", response)
    val json: JSONObject = JSONObject(response)
    val imageAsString = json.getString("Image")
    var byteArray: ByteArray? = Base64.decode(imageAsString, Base64.DEFAULT)
    val bmp = if (byteArray == null) null else byteArrayToBitmap(byteArray)

    client.close()

    return bmp
}

suspend fun readJSONString(inputStream: InputStream): String {
    while(inputStream.available() < 4) { }

    val sizeInBytes = readBytes(inputStream, 500, 4)
    val size = byteArrayToInt32(sizeInBytes)

    while(inputStream.available() < size) { }

    val stringInBytes = readBytes(inputStream, 500, size)
    return String(stringInBytes) //ASCII
}

fun writeJSONString(outputStream: OutputStream, toWrite: String) {
    outputStream.write(numberToByteArray(toWrite.length))
    outputStream.write(toWrite.toByteArray())
    outputStream.flush()
}

private fun readBytes(stream: InputStream, bufferSize: Int, bytesToRead: Int): ByteArray {
    val data = ByteArray(bytesToRead)
    var bytesRead: Int
    var bytesReadTotal = 0

    do {
        bytesRead = stream.read(data, bytesReadTotal, Math.min(bufferSize, bytesToRead - bytesReadTotal))
        bytesReadTotal += bytesRead
    }
    while (bytesRead != 0 && bytesReadTotal != bytesToRead)

    return data
}