package com.gareth.weatherapplication.database.mappers

import com.gareth.weatherapplication.database.models.WeatherCacheEntity
import com.gareth.weatherapplication.domain.models.WeatherProperty
import com.gareth.weatherapplication.util.EntityMapper
import javax.inject.Inject

class WeatherCacheMapper
@Inject
constructor(): EntityMapper<WeatherCacheEntity, WeatherProperty> {
    override fun mapFromEntity(entity: WeatherCacheEntity): WeatherProperty {
        return WeatherProperty(
            id = entity.id,
            date = entity.date,
            weatherState = entity.weatherState,
            weatherStateAbbreviation = entity.weatherStateAbbreviation,
            temperature = entity.temperature
        )
    }

    override fun mapToEntity(domainModel: WeatherProperty): WeatherCacheEntity {
        return WeatherCacheEntity(
            id = domainModel.id,
            date = domainModel.date,
            weatherState = domainModel.weatherState,
            weatherStateAbbreviation = domainModel.weatherStateAbbreviation,
            temperature = domainModel.temperature
        )
    }

    fun mapFromCacheList(entities: List<WeatherCacheEntity>): List<WeatherProperty>{
        return entities.map { mapFromEntity(it) }
    }
}