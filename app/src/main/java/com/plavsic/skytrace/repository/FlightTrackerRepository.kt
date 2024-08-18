package com.plavsic.skytrace.repository

import com.plavsic.skytrace.BuildConfig
import com.plavsic.skytrace.model.data.FlightResponse
import com.plavsic.skytrace.model.remote.RetrofitInstance

class FlightTrackerRepository {
    suspend fun getFlights(limit:Int = 1):List<FlightResponse>{
        return RetrofitInstance.api.getFlights(
            apiKey = BuildConfig.AVIATION_EDGE_API_KEY,
            limit = limit)
    }
}