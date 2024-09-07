package com.plavsic.skytrace.features.aircraft.repository

import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity
import com.plavsic.skytrace.utils.resource.UIState

interface AircraftRepository {

    suspend fun getAircraft():UIState<List<AircraftEntity>>
}