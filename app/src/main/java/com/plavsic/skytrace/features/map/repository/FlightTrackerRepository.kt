package com.plavsic.skytrace.features.map.repository

import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.utils.resource.UIState

interface FlightTrackerRepository {
    suspend fun getFlights(limit:Int): UIState<List<FlightResponse>>
}