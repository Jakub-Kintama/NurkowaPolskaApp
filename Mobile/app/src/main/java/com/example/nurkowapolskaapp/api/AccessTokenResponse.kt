package com.example.nurkowapolskaapp.api

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenResponse(
    @JsonProperty("email") val email: String,
    @JsonProperty("role") val role: List<Role>,
    @JsonProperty("accessToken") val accessToken: String,
    @JsonProperty("refreshToken") val refreshToken: String
)

data class Role(
    @JsonProperty("authority") val authority: String
)