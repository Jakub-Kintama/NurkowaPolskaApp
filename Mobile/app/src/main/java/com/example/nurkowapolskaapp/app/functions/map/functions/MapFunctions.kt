package com.example.nurkowapolskaapp.app.functions.map.functions

import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.util.Log
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

fun getAddress(lat: Double, lng: Double, context: Context, callback: (String) -> Unit) {
    val geocoder = Geocoder(context, Locale.getDefault())
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geocoder.getFromLocation(lat, lng, 1) { addresses ->
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                val subLocality = address.countryName.toString()
                Log.d("address33locality", subLocality)

                callback(subLocality)
            } else {
                callback("default")
            }
        }
    }
}