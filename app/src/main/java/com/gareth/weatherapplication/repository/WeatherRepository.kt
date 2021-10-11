package com.gareth.weatherapplication.repository

import com.gareth.weatherapplication.database.WeatherDatabaseDao
import com.gareth.weatherapplication.database.mappers.WeatherCacheMapper
import com.gareth.weatherapplication.domain.state.DataState
import com.gareth.weatherapplication.network.WeatherApiService
import com.gareth.weatherapplication.network.mappers.WeatherNetworkMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class WeatherRepository
@Inject constructor(
    private val weatherService: WeatherApiService,
    private val weatherDao: WeatherDatabaseDao,
    private val weatherNetworkMapper: WeatherNetworkMapper,
    private val weatherCacheMapper: WeatherCacheMapper
    ) {

    suspend fun getWeather() = weatherCacheMapper.mapFromCacheList(weatherDao.getWeather())

    suspend fun updateWeatherData(locationTitle: String) = flow {
        try {
            emit(DataState.Loading)
            val locationInfo = weatherService.getLocationInfo(locationTitle).find { it.location == locationTitle }
            locationInfo?.let {
                val networkWeatherList = weatherService.getFiveDayWeather(it.locationId).weatherEntityList
                weatherDao.deleteWeather()
                weatherNetworkMapper.mapFromEntityList(networkWeatherList)
                    .onEach { weatherProperty ->
                    weatherDao.insertWeather(weatherCacheMapper.mapToEntity(weatherProperty))
                }
            }

            val domainWeatherList = weatherCacheMapper.mapFromCacheList(weatherDao.getWeather())
            emit(DataState.Success(domainWeatherList))
        } catch (exception: Exception) {
            emit(DataState.Error(exception))
        }
    }
}