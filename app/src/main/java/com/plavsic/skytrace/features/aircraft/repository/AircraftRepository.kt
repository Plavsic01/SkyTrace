package com.plavsic.skytrace.features.aircraft.repository

import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity

interface AircraftRepository {

    suspend fun getAircrafts():List<AircraftEntity>?
}