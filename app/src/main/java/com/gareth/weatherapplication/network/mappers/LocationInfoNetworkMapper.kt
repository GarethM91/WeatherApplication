package com.gareth.weatherapplication.network.mappers

import com.gareth.weatherapplication.domain.models.LocationInfoProperty
import com.gareth.weatherapplication.network.models.LocationInfoNetworkEntity
import com.gareth.weatherapplication.util.EntityMapper
import javax.inject.Inject

class LocationInfoNetworkMapper
@Inject
constructor()
    : EntityMapper<LocationInfoNetworkEntity, LocationInfoProperty> {

    override fun mapFromEntity(entity: LocationInfoNetworkEntity): LocationInfoProperty {
        return LocationInfoProperty(
            locationId = entity.locationId,
            location = entity.location,
            locationType = entity.locationType,
        )
    }

    override fun mapToEntity(domainModel: LocationInfoProperty): LocationInfoNetworkEntity {
        return LocationInfoNetworkEntity(
            locationId = domainModel.locationId,
            location = domainModel.location,
            locationType = domainModel.locationType
        )
    }
}