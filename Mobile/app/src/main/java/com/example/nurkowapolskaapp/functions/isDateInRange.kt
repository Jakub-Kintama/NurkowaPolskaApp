package com.example.nurkowapolskaapp.functions

import java.time.LocalDate

// check if marker date is withing current filterOptions DateRange
fun isDateInRange(date: LocalDate, startDate: LocalDate, endDate: LocalDate): Boolean {
    return date.isEqual(startDate) || date.isEqual(endDate) || (date.isAfter(startDate) && date.isBefore(endDate))
}