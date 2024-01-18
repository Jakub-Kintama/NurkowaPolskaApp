package com.example.nurkowapolskaapp.functions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun base64ToBitmap(base64String: String?): Bitmap? {
    if (base64String.isNullOrEmpty()) {
        return null
    }

    try {
        val imageBytes: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}