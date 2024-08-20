package com.plavsic.skytrace.common.repository

import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.utils.UIState

interface FlightTrackerRepository {
    suspend fun getFlights(limit:Int):UIState<List<FlightResponse>>
}