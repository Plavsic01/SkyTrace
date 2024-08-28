package com.plavsic.skytrace.common.remoteService


import com.plavsic.skytrace.features.futureFlight.dto.FutureFlightResponseDTO
import com.plavsic.skytrace.features.map.dto.FlightResponseDTO
import com.plavsic.skytrace.features.schedule.dto.ScheduleResponseDTO
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface FlightService {
    @GET("flights")
    suspend fun getFlights(
        @Query("key") apiKey:String,
        @Query("status") status:String,
        @Query("limit") limit:Int
    ): Response<List<FlightResponseDTO>>


    @GET("timetable")
    suspend fun getSchedules(
        @Query("key") apiKey:String,
        @Query("flight_num") flightNum:String,
        @Query("flight_iata") flightIata:String,
        @Query("status") status:String
    ):Response<List<ScheduleResponseDTO>>


    @GET("flightsFuture")
    suspend fun getFutureFlights(
        @Query("key") apiKey:String,
        @Query("iataCode") iataCode:String,
        @Query("type") type:String,
        @Query("date") date:String,
        @Query("airline_iata") airlineIata:String? = null,
        @Query("flight_num") flightNum:String? = null
    ):Response<List<FutureFlightResponseDTO>>

}