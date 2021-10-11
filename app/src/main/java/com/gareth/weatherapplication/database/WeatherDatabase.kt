package com.gareth.weatherapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gareth.weatherapplication.database.models.WeatherCacheEntity

@Database(entities = [WeatherCacheEntity::class], version = 4, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDatabaseDao(): WeatherDatabaseDao

    companion object {
        const val DATABASE_NAME: String = "weather_database"
    }
}