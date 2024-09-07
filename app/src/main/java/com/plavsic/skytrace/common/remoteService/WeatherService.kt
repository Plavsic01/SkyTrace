package com.plavsic.skytrace.common.remoteService

import com.plavsic.skytrace.features.weather.dto.WeatherResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("appid") apiKey:String,
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("units") units:String
    ):Response<WeatherResponseDTO>
}