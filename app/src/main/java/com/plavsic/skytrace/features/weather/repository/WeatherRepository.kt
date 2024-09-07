package com.plavsic.skytrace.features.weather.repository


import com.plavsic.skytrace.features.weather.model.WeatherResponse
import com.plavsic.skytrace.utils.resource.UIState


interface WeatherRepository {
    suspend fun getWeather(
        lat:Double,
        lon:Double,
        units:String
    ): UIState<WeatherResponse>
}