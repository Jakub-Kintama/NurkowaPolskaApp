package com.example.nurkowapolskaapp.functions

import android.content.Context
import android.location.Geocoder
import java.util.Locale

// TODO: Find alternative to deprecated method
@Suppress("DEPRECATION")
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