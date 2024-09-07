package com.plavsic.skytrace.features.airport.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val codeIataCity: String,
    val GMT: String,
    val latitudeCity: Double,
    val longitudeCity: Double,
    val nameCity: String
)
