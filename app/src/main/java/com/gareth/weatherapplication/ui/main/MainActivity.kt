package com.gareth.weatherapplication.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.gareth.weatherapplication.ui.theme.WeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val weatherViewModel by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApplicationTheme {
                WeatherActivityScreen(weatherViewModel)
            }
        }
    }
}

@Composable
private fun WeatherActivityScreen(weatherViewModel: WeatherViewModel) {
    WeatherScreen(
        weatherForecast = weatherViewModel.weatherForecast,
        errorStatus = weatherViewModel.errorState.value,
        isRefreshing = weatherViewModel.isRefreshing.value,
        isLoading = weatherViewModel.isLoading.value,
        location = weatherViewModel.location,
        weatherUpdate = weatherViewModel::pullToRefresh)
}