package com.plavsic.skytrace.features.weather.dto

data class WeatherInfoDTO(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)
