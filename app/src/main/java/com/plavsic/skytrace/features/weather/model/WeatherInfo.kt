package com.plavsic.skytrace.features.weather.model

data class WeatherInfo(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)
