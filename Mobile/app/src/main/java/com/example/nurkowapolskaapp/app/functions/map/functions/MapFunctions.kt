package com.example.nurkowapolskaapp.app.functions.map.functions

import android.content.Context
import android.location.Geocoder
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

fun getCurrentDate(): String {
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        currentDateTime.format(formatter)
    } else {
        val calendar = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formatter.format(calendar)
    }
}

fun getAddress(lat: Double, lng: Double, context: Context): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(lat, lng, 1)
        if (addresses!!.isNotEmpty()) {
            val address = addresses[0]
            val locationName = address.locality ?: address.subAdminArea ?: address.adminArea ?: ""
            locationName
        } else {
            "Location name not found"
        }
    } catch (e: Exception) {
        "Error retrieving location name"
    }
}