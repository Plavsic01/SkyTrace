package com.plavsic.skytrace.features.airport.data.remote

import com.plavsic.skytrace.features.airport.dto.AirportDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirportService {

    @GET("airportDatabase")
    suspend fun getAirportByCode(
        @Query("key") apiKey:String,
        @Query("codeIataAirport") codeIataAirport:String
    ):Response<List<AirportDTO>>
}