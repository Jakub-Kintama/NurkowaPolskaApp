package com.example.nurkowapolskaapp.map.model

import java.time.LocalDate

data class DateRange(
    var dateBegin: LocalDate = LocalDate.now().withDayOfYear(1),
    var dateEnd: LocalDate = LocalDate.ofYearDay(LocalDate.now().year, LocalDate.now().lengthOfYear())

)