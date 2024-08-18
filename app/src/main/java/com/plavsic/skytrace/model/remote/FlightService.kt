package com.plavsic.skytrace.model.remote


import com.plavsic.skytrace.model.data.FlightResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface FlightService {
    @GET("flights")
    suspend fun getFlights(
        @Query("key") apiKey:String,
        @Query("limit") limit:Int
    ): List<FlightResponse>


}