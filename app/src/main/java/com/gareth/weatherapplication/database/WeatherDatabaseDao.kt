package com.gareth.weatherapplication.database

import androidx.room.*
import com.gareth.weatherapplication.database.models.WeatherCacheEntity

@Dao
interface WeatherDatabaseDao {
    @Query("SELECT * FROM weather_forecast ORDER BY applicable_date ASC")
    suspend fun getWeather(): List<WeatherCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherCacheEntity)

    @Query("DELETE FROM weather_forecast")
    suspend fun deleteWeather()
}