package com.plavsic.skytrace.features.aircraft.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aircraft")
data class AircraftEntity(
    @PrimaryKey val airplaneId: Int,
    val airplaneIataType: String,
    val enginesCount: String,
    val enginesType: String,
    val firstFlight: String,
    val modelCode: String,
    val numberRegistration: String,
    val planeAge: String,
    val planeModel: String,
    val planeOwner: String,
    val planeStatus: String,
    val productionLine: String,
    val registrationDate: String
)