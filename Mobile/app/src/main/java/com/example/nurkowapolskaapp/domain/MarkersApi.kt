package com.example.nurkowapolskaapp.domain

import com.example.nurkowapolskaapp.app.functions.map.Marker
import retrofit2.Call
import retrofit2.http.GET

interface MarkersApi {

    @GET("api/markers")
    fun getMarkers(): Call<List<Marker>>
}