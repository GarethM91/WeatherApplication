package com.gareth.weatherapplication.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_forecast")
data class WeatherCacheEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,

    @ColumnInfo(name = "applicable_date")
    val date: String,

    @ColumnInfo(name = "weather_state_name")
    val weatherState: String,

    @ColumnInfo(name = "weather_state_abbr")
    val weatherStateAbbreviation: String,

    @ColumnInfo(name = "the_temp")
    val temperature: Int
    )
