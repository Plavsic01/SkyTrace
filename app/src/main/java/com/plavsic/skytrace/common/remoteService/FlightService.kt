package com.plavsic.skytrace.common.remoteService


import com.plavsic.skytrace.features.map.dto.FlightResponseDTO
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface FlightService {
    @GET("flights")
    suspend fun getFlights(
        @Query("key") apiKey:String,
        @Query("limit") limit:Int
    ): Response<List<FlightResponseDTO>>

}