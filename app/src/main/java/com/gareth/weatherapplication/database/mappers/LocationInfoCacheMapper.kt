package com.gareth.weatherapplication.database.mappers

import com.gareth.weatherapplication.database.models.LocationInfoCacheEntity
import com.gareth.weatherapplication.domain.models.LocationInfoProperty
import com.gareth.weatherapplication.util.EntityMapper
import javax.inject.Inject

class LocationInfoCacheMapper
@Inject
constructor() : EntityMapper<LocationInfoCacheEntity, LocationInfoProperty> {
    override fun mapFromEntity(entity: LocationInfoCacheEntity): LocationInfoProperty {
        return LocationInfoProperty(
            locationId = entity.locationId,
            location = entity.location,
            locationType = entity.locationType,
        )
    }

    override fun mapToEntity(domainModel: LocationInfoProperty): LocationInfoCacheEntity {
        return LocationInfoCacheEntity(
            locationId = domainModel.locationId,
            location = domainModel.location,
            locationType = domainModel.locationType,
        )
    }
}