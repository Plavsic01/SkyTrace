package com.plavsic.skytrace.features.map.repository

import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.utils.resource.UIState
import retrofit2.http.Query


interface FlightTrackerRepository {
    suspend fun getFlights(
        lat:Double,
        lng:Double,
        distance:Int,
        limit:Int
    ): UIState<List<FlightResponse>>
}