package com.gareth.weatherapplication.domain.models

import java.time.LocalDate

data class WeatherProperty(
    val id: Long,
    val date: String,
    val weatherState: String,
    val weatherStateAbbreviation: String,
    val temperature: Int
    ) {

    val imageUrl get() = "https://www.metaweather.com/static/img/weather/png/$weatherStateAbbreviation.png"

    private val dateValue = LocalDate.parse(date)
    val day: String get() = dateValue.dayOfWeek.toString()
    val dateMonth: String get() = "${dateValue.dayOfMonth} ${dateValue.month}"
}
