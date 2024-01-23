package com.example.nurkowapolskaapp.map.model

data class MarkerFilterOptions(
    var showCrayfish: Boolean = true,
    var showOther: Boolean = false,
    var showUnverified: Boolean = false,
    var showVerified: Boolean = true,
    var dateRange: DateRange
)