package com.plavsic.skytrace.features.airport.data.remote

import com.plavsic.skytrace.features.airport.dto.CityDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {

    @GET("cityDatabase")
    suspend fun getCityByCode(
        @Query("key") apiKey:String,
        @Query("codeIataCity") codeIataCity: String
    ): Response<List<CityDTO>>
}