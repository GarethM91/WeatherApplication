package com.gareth.weatherapplication.network

import com.gareth.weatherapplication.network.models.ConsolidatedWeather
import com.gareth.weatherapplication.network.models.LocationInfoNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {

    @GET("search/?query=Belfast")
    suspend fun getLocationInfoo(): List<LocationInfoNetworkEntity>

    @GET("search/")
    suspend fun getLocationInfo(@Query("query") location: String): List<LocationInfoNetworkEntity>

    @GET("{locationId}/")
    suspend fun getFiveDayWeather(@Path("locationId") locationId: Int): ConsolidatedWeather
}
