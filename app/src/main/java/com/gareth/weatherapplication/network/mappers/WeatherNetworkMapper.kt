package com.gareth.weatherapplication.network.mappers

import com.gareth.weatherapplication.domain.models.WeatherProperty
import com.gareth.weatherapplication.network.models.ConsolidatedWeatherNetworkEntity
import com.gareth.weatherapplication.util.EntityMapper
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherNetworkMapper
@Inject
constructor(): EntityMapper<ConsolidatedWeatherNetworkEntity, WeatherProperty> {
    override fun mapFromEntity(entity: ConsolidatedWeatherNetworkEntity): WeatherProperty {
        return WeatherProperty(
            id = entity.id,
            date = entity.date,
            weatherState = entity.weatherState,
            weatherStateAbbreviation = entity.weatherStateAbbreviation,
            temperature = entity.temperature.roundToInt()
        )
    }

    override fun mapToEntity(domainModel: WeatherProperty): ConsolidatedWeatherNetworkEntity {
        return ConsolidatedWeatherNetworkEntity(
            id = domainModel.id,
            date = domainModel.date,
            weatherState = domainModel.weatherState,
            weatherStateAbbreviation = domainModel.weatherStateAbbreviation,
            temperature = domainModel.temperature.toDouble()
        )
    }

    fun mapFromEntityList(entities: List<ConsolidatedWeatherNetworkEntity>): List<WeatherProperty>{
        return entities.map { mapFromEntity(it) }
    }
}