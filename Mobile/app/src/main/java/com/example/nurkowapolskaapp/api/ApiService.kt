package com.example.nurkowapolskaapp.api

import com.example.nurkowapolskaapp.map.model.Marker
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("api/markers")
    fun getMarkers(): Call<List<Marker>>

    @POST("api/markers")
    fun addMarker(@Header("Authorization") authHeader: String, @Body requestBody: RequestBody): Call<Marker>

    @GET("/api/auth/exchange/{idtoken}")
    fun getAccessToken(@Path("idtoken") idToken: String): Call<AccessTokenResponse>

    @DELETE("/api/markers/{markerId}")
    fun deleteMarker(@Header("Authorization") authHeader: String, @Path("markerId") markerId: String): Call<Void>

    @PATCH("api/admin/markers")
    fun updateMarker(@Header("Authorization") authHeader: String, @Body updatedMarker: RequestBody): Call<Marker>
//    @PATCH("api/admin/markers")
//    fun updateMarker(@Header("Authorization") authHeader: String, @Body updatedMarker: RequestBody): Call<Marker>
}