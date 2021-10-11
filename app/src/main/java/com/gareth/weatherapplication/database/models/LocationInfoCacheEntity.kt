package com.gareth.weatherapplication.database.models

import androidx.room.*

@Entity(tableName = "location_info")
data class LocationInfoCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "woeid")
    val locationId: Int,

    @ColumnInfo(name = "title")
    val location: String,

    @ColumnInfo(name = "location_type")
    val locationType: String
    )
