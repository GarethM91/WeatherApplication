package com.gareth.weatherapplication.network.models
import com.squareup.moshi.Json

data class ConsolidatedWeatherNetworkEntity(
    val id: Long,
    @Json(name = "applicable_date") val date: String,
    @Json(name = "weather_state_name") val weatherState: String,
    @Json(name = "weather_state_abbr") val weatherStateAbbreviation: String,
    @Json(name = "the_temp") val temperature: Double
)

