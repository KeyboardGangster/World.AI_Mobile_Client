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

suspend fun requestImages(prompt: String, key: String): List<Bitmap> {
    val client = Socket("10.0.2.2", 9999)
    val inputStream = client.getInputStream().buffered()
    val outputStream = client.getOutputStream().buffered()

    Log.d("JsonP2P", "Connected!!!!")
    val jsonReq = JSONObject()
    jsonReq.accumulate("Prompt", prompt)
    jsonReq.accumulate("Key", key)

    writeJSONString(outputStream, jsonReq.toString())
    Log.d("JsonP2P", "Sent request!!!!")
    val response: String = readJSONString(inputStream)
    Log.d("JsonP2P", "Got response!!!!")
    val json: JSONObject = JSONObject(response)
    Log.d("JsonP2P", "Converted to Json!!!!")

    val images: MutableList<Bitmap> = mutableListOf()

    for (i: Int in 1..5) {
        val imageAsString = json.getString("Image$i")
        var byteArray: ByteArray = Base64.decode(imageAsString, Base64.DEFAULT)
        val bmp = byteArrayToBitmap(byteArray)
        images.add(bmp)
        Log.d("JsonP2P", "Image$i converted")
    }

    client.close()

    return images
}

suspend fun readJSONString(inputStream: InputStream): String {

    Log.d("JsonP2P", "Waiting for length!!!!")
    while(inputStream.available() < 4) { }

    val sizeInBytes = readBytes(inputStream, 500, 4)
    val size = byteArrayToInt32(sizeInBytes)

    Log.d("JsonP2P", "reading...")
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
        val toRead = Math.min(bufferSize, bytesToRead - bytesReadTotal)
        while(stream.available() < toRead) {
            Log.d("JsonP2P", "waiting... ${stream.available()}/$toRead")
        }


        Log.d("JsonP2P", "read from stream")
        bytesRead = stream.read(data, bytesReadTotal, toRead)
        bytesReadTotal += bytesRead
    }
    while (bytesRead != 0 && bytesReadTotal != bytesToRead)

    return data
}