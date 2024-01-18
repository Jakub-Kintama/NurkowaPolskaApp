package com.example.nurkowapolskaapp.functions

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream

fun convertImageToBase64(uri: Uri?, context: Context): String? {
    try {
        val contentResolver: ContentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri!!)
        val bitmap: Bitmap? = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        if (bitmap != null) {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
            val imageBytes: ByteArray = outputStream.toByteArray()

            return Base64.encodeToString(imageBytes, Base64.DEFAULT)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}