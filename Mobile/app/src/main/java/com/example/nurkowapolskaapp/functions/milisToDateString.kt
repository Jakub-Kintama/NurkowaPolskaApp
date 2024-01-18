package com.example.nurkowapolskaapp.functions

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun millisToDateString(millis: Long): String? {
    val instant = Instant.ofEpochMilli(millis)
    val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return localDate.format(formatter)
}