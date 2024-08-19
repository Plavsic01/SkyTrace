package com.plavsic.skytrace.common.remote


import com.plavsic.skytrace.features.map.model.FlightResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface FlightService {
    @GET("flights")
    suspend fun getFlights(
        @Query("key") apiKey:String,
        @Query("limit") limit:Int
    ): List<FlightResponse>


}