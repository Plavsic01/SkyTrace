package com.plavsic.skytrace.features.map.repository

import com.plavsic.skytrace.BuildConfig
import com.plavsic.skytrace.common.remote.RetrofitInstance
import com.plavsic.skytrace.common.repository.FlightTrackerRepository
import com.plavsic.skytrace.features.map.model.FlightResponse

class FlightTrackerRepositoryImpl : FlightTrackerRepository {
    override suspend fun getFlights(limit:Int):List<FlightResponse>{
        return RetrofitInstance.api.getFlights(
            apiKey = BuildConfig.AVIATION_EDGE_API_KEY,
            limit = limit)
    }
}