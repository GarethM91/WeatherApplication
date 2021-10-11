package com.gareth.weatherapplication.di

import com.gareth.weatherapplication.database.WeatherDatabaseDao
import com.gareth.weatherapplication.database.mappers.WeatherCacheMapper
import com.gareth.weatherapplication.network.WeatherApiService
import com.gareth.weatherapplication.network.mappers.WeatherNetworkMapper
import com.gareth.weatherapplication.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherService: WeatherApiService,
        weatherDao: WeatherDatabaseDao,
        weatherNetworkMapper: WeatherNetworkMapper,
        weatherCacheMapper: WeatherCacheMapper
    ) : WeatherRepository{
        return WeatherRepository(weatherService, weatherDao, weatherNetworkMapper, weatherCacheMapper)
    }
}