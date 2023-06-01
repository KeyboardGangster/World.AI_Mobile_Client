package com.example.movieApp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

fun checkExternalRWPermissions(context: Context, launcher: ActivityResultLauncher<Array<String>>): Boolean {
    if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED) {
        return true
    }
    else {
        launcher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        return false
    }
}

fun handleExternalWrite(context: Context, launcher: ActivityResultLauncher<Array<String>>, onPermsGiven: () -> Unit) {
    if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        onPermsGiven()
    }
    else {
        // You can directly ask for the permission.
        // The registered ActivityResultCallback gets the result of this request.
        launcher.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }
}

fun handleExternalRead(context: Context, launcher: ActivityResultLauncher<Array<String>>, onPermsGiven: () -> Unit) {
    if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        onPermsGiven()
    }
    else {
        // You can directly ask for the permission.
        // The registered ActivityResultCallback gets the result of this request.
        launcher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    }
}