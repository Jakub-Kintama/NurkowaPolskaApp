package com.example.nurkowapolskaapp.functions

import java.time.format.DateTimeFormatter

// change milliseconds to LocalDate and to String in polish date format
fun millisToDateString(millis: Long): String? {
    val localDate = convertMillisToLocalDate(millis)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return localDate.format(formatter)
}