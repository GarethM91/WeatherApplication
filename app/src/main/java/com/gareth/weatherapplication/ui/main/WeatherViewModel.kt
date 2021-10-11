package com.gareth.weatherapplication.ui.main

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gareth.weatherapplication.domain.models.WeatherProperty
import com.gareth.weatherapplication.domain.state.DataState
import com.gareth.weatherapplication.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LoadingType { INITIAL, REFRESH }

@HiltViewModel
class WeatherViewModel
@Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {
    var isRefreshing = mutableStateOf(false)
        private set

    var errorState = mutableStateOf(false)
        private set

    var isLoading = mutableStateOf(false)
        private set

    var weatherForecast = mutableStateListOf<WeatherProperty>()
        private set

    var location = "Belfast"
        private set

    private var oldWeatherList = listOf<WeatherProperty>()

    init {
        updateWeather(LoadingType.INITIAL)
    }

    private fun updateWeather(loadingType: LoadingType) {
        viewModelScope.launch {
            oldWeatherList = weatherRepository.getWeather()
            weatherRepository.updateWeatherData(location)
                .onEach { dataState ->
                    when(dataState) {
                        is DataState.Success<List<WeatherProperty>> ->  {
                            updateWeatherForecast(dataState.data)
                            errorState.value = false
                            isRefreshing.value = false
                            isLoading.value = false
                        }
                        is DataState.Error -> {
                            Log.e("WEATHER_UPDATE_ERROR", dataState.exception.message.toString())
                            if (oldWeatherList.isNotEmpty() && weatherForecast.isEmpty())
                                updateWeatherForecast(oldWeatherList)

                            errorState.value = true
                            isRefreshing.value = false
                            isLoading.value = false
                        }
                        is DataState.Loading -> {
                            when(loadingType) {
                                LoadingType.INITIAL -> isLoading.value = true
                                LoadingType.REFRESH -> isRefreshing.value = true
                            }

                            errorState.value = false
                        }
                    }
                } .launchIn(viewModelScope)
        }
    }

    fun pullToRefresh() {
        updateWeather(LoadingType.REFRESH)
    }

    private fun updateWeatherForecast(newWeatherList: List<WeatherProperty>) {
        if(weatherForecast.isNotEmpty())
            weatherForecast.removeAll(oldWeatherList)

        weatherForecast.addAll(newWeatherList)
    }
}