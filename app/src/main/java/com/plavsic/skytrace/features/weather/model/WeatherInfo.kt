package com.plavsic.skytrace.features.weather.model

data class WeatherInfo(
    val temp: Int,
    val feels_like: Int,
    val temp_min: Int,
    val temp_max: Int,
    val pressure: Int,
    val humidity: Int
)
