package com.plavsic.skytrace.features.aircraft.data.remote

import com.plavsic.skytrace.features.aircraft.dto.AircraftDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AircraftService {
    @GET("airplaneDatabase")
    suspend fun getAircraft(
        @Query("key") apiKey:String
    ):Response<List<AircraftDTO>>
}