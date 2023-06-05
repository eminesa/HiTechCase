package com.eminesa.hitechcase

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

fun getCaptureImageOutputFile(activity: Activity): File {
    val imageFile = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File(imageFile, Date().time.toString() + ".jpg").also {
        println("GetCaptureImageOutputFile: File absolute path ${it.absolutePath}")
    }
}

fun getCaptureImageOutputUri(file: File, context: Context): Uri {
    return FileProvider.getUriForFile(
        context,
        context.applicationInfo.packageName.toString() + ".fileprovider",
        file
    ).also {
        println("GetCaptureImageOutputUri: Uri ${it.path}")
    }
}
