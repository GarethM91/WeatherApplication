package com.gareth.weatherapplication.network.models

import com.squareup.moshi.Json

data class ConsolidatedWeather(
    @Json(name = "consolidated_weather") val weatherEntityList: List<ConsolidatedWeatherNetworkEntity>
)
