package com.example.nurkowapolskaapp.functions

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

// Change milliseconds to LocalDate
fun convertMillisToLocalDate(milliseconds: Long): LocalDate {
    val instant = Instant.ofEpochMilli(milliseconds)
    val zonedDateTime = instant.atZone(ZoneId.systemDefault())
    return zonedDateTime.toLocalDate()
}