package com.gareth.weatherapplication.di

import android.content.Context
import androidx.room.Room
import com.gareth.weatherapplication.database.WeatherDatabase
import com.gareth.weatherapplication.database.WeatherDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room
            .databaseBuilder(
                context,
                WeatherDatabase::class.java,
                WeatherDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDatabaseDao(weatherDatabase: WeatherDatabase): WeatherDatabaseDao {
        return weatherDatabase.weatherDatabaseDao()
    }

}