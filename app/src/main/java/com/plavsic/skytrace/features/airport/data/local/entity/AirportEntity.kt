package com.plavsic.skytrace.features.airport.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "airports",
    foreignKeys = [ForeignKey(
        entity = CityEntity::class,
        parentColumns = ["codeIataCity"],
        childColumns = ["codeIataCity"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AirportEntity(
    @PrimaryKey val codeIataAirport: String,
    val GMT: String,
    val codeIataCity: String,
    val latitudeAirport: Double,
    val longitudeAirport: Double,
    val nameAirport: String,
    val nameCountry: String,
    val phone: String?
)
