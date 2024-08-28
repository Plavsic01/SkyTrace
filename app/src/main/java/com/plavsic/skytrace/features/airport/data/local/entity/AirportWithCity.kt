package com.plavsic.skytrace.features.airport.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AirportWithCity(
    @Embedded val airport: AirportEntity,

    @Relation(
        parentColumn = "codeIataCity",
        entityColumn = "codeIataCity"
    )
    val city: CityEntity
)
