package com.plavsic.skytrace.common.repository

import com.plavsic.skytrace.features.map.model.FlightResponse

interface FlightTrackerRepository {
    suspend fun getFlights(limit:Int):List<FlightResponse>
}