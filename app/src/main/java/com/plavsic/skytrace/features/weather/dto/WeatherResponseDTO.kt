package com.plavsic.skytrace.features.weather.dto

data class WeatherResponseDTO(
    val coord:CoordinateDTO,
    val weather: List<WeatherDTO>,
    val main: WeatherInfoDTO,
    val visibility:Int,
    val wind: WindDTO,
    val sys:SystemInfoDTO,
    val timezone:Int
)
