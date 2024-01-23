package com.example.nurkowapolskaapp.api

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nurkowapolskaapp.map.model.Marker
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class ViewModelApi : ViewModel() {
    private var jSessionId: String? = null

    private fun setJSessionId(sessionId: String) {
        jSessionId = sessionId
    }

    private fun getJSessionId(): String? {
        return jSessionId
    }

    private val _markerList = mutableStateOf<List<Marker>>(emptyList())
    val markerList: State<List<Marker>> = _markerList

    private val baseUrl = "http://172.19.100.10.nip.io:8080/"
    private val tag: String = "CHECK_RESPONSE"

    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

    private val apiService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .build()

    fun getMarkers() {
        val fetchService = apiService.create(ApiService::class.java)

        viewModelScope.launch {
            fetchService.getMarkers().enqueue(object: Callback<List<Marker>> {
                override fun onResponse(call: Call<List<Marker>>, response: Response<List<Marker>>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            _markerList.value = it
                        }
                    }
                    Log.i(tag+"_FETCH", "onResponse: isSuccessful")
                }

                override fun onFailure(call: Call<List<Marker>>, t: Throwable) {
                    Log.e(tag+"_FETCH", "onFailure: ${t.message}")
                }
            })
        }
    }

    fun exchangeIdTokenForAccessToken(idToken: String) {
        val exchangeService = apiService.create(ApiService::class.java)

        val call = exchangeService.getAccessToken(idToken)

        Log.d(tag + "_EXCHANGE", "$call")
        call.enqueue(object : Callback<AccessTokenResponse> {
            override fun onResponse(
                call: Call<AccessTokenResponse>,
                response: Response<AccessTokenResponse>
            ) {
                if (response.isSuccessful) {
                    val jwtToken = response.body()?.accessToken
                    jwtToken?.let { setJSessionId(it) }
                    Log.i(tag + "_EXCHANGE", "${response.code()}")
                    Log.i(tag + "_EXCHANGE", "${getJSessionId()}")
                    Log.i(tag + "_EXCHANGE", "onResponse: isSuccessful ${response.body()}")
                } else {
                    Log.i(tag + "_EXCHANGE", "onResponse: isFailure ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                Log.e(tag + "_EXCHANGE", "onFailure: ${t.message}")
            }
        })
    }


    fun addMarker(
        marker: Marker,
        viewModelApi: ViewModelApi
    ) {
        val addService = apiService.create(ApiService::class.java)

        val jacksonString = """
            {
            "_id": ${marker.id},
            "mapMarker": {
            "position": {
              "lat": ${marker.mapMarker.position.lat},
              "lng": ${marker.mapMarker.position.lng}
            },
            "title": "${marker.mapMarker.title}",
            "description": "${marker.mapMarker.description}"
            },
            "userEmail": "${marker.userEmail}",
            "CrayfishType": "${marker.CrayfishType}",
            "date": "${marker.date}",
            "verified": ${marker.verified},
            "image": {
            "name": "${marker.image?.name}",
            "data": "${marker.image?.data}"
            }
            }
        """.trimIndent()

        val idToken = getJSessionId()

        val authHeader = "Bearer $idToken"

        val requestBody = jacksonString.toRequestBody("application/json".toMediaType())

        val call = addService.addMarker(authHeader, requestBody)

        call.enqueue(object : Callback<Marker> {
            override fun onResponse(call: Call<Marker>, response: Response<Marker>) {
                if(response.isSuccessful) {
                    Log.i(tag + "_ADD_MARKER", "onResponse: isSuccessful ${response.code()}")
                    viewModelApi.getMarkers()
                } else {
                    Log.i(tag + "_ADD_MARKER", "onResponse: notSuccessful ${response.code()}")
                }
            }
            override fun onFailure(call: Call<Marker>, t: Throwable) {
                Log.e(tag + "_ADD_MARKER", "onFailure: ${t.message}")
            }
        })
    }

    fun updateMarker(updatedMarker: Marker, viewModelApi: ViewModelApi) {
        val updateService = apiService.create(ApiService::class.java)

        val jacksonString = """
    {
        "_id": "${updatedMarker.id}",
        "mapMarker": {
            "position": {
                "lat": ${updatedMarker.mapMarker.position.lat},
                "lng": ${updatedMarker.mapMarker.position.lng}
            },
            "title": "${updatedMarker.mapMarker.title}",
            "description": "${updatedMarker.mapMarker.description}"
        },
        "userEmail": "${updatedMarker.userEmail}",
        "CrayfishType": "${updatedMarker.CrayfishType}",
        "date": "${updatedMarker.date}",
        "verified": ${updatedMarker.verified},
        "image": {
            "name": "${updatedMarker.image?.name}",
            "data": "${updatedMarker.image?.data}"
        }
    }
""".trimIndent()

        val idToken = getJSessionId()

        val authHeader = "Bearer $idToken"

        val requestBody = jacksonString.toRequestBody("application/json".toMediaType())

        val call = updateService.updateMarker(authHeader, requestBody)

        call.enqueue(object : Callback<Marker> {
            override fun onResponse(call: Call<Marker>, response: Response<Marker>) {
                if(response.isSuccessful) {
                    Log.i(tag + "_UPDATE_MARKER", "onResponse: isSuccessful ${response.code()}")
                    Log.i(tag + "_UPDATE_MARKER", "${response.body()}")
                    viewModelApi.getMarkers()
                } else {
                    Log.i(tag + "_UPDATE_MARKER", "onResponse: notSuccessful ${response.code()}")
                }
            }
            override fun onFailure(call: Call<Marker>, t: Throwable) {
                Log.e(tag + "_UPDATE_MARKER", "onFailure: ${t.message}")
            }
        })
    }

    fun deleteMarker(markerId: String, viewModelApi: ViewModelApi) {
        val deleteService = apiService.create(ApiService::class.java)

        val idToken = getJSessionId()
        val authHeader = "Bearer $idToken"

        val call = deleteService.deleteMarker(authHeader, markerId)

        call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) {
                    Log.i(tag + "_DELETE_MARKER", "onResponse: isSuccessful ${response.code()}")
                    viewModelApi.getMarkers()
                } else {
                    Log.i(tag + "_DELETE_MARKER", "onResponse: notSuccessful ${response.code()}")
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(tag + "_DELETE_MARKER", "onFailure: ${t.message}")
            }
        })
    }
}