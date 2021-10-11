package com.gareth.weatherapplication.util

interface EntityMapper <Entity, DomainModel>{
        fun mapFromEntity(entity: Entity): DomainModel
        fun mapToEntity(domainModel: DomainModel): Entity
}