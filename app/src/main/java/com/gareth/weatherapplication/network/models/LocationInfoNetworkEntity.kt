package com.gareth.weatherapplication.network.models
import com.squareup.moshi.Json

data class LocationInfoNetworkEntity(
    @Json(name = "woeid") val locationId: Int,
    @Json(name = "title") val location: String,
    @Json(name = "location_type") val locationType: String
)

