package com.plavsic.skytrace.features.weather.model

data class WeatherResponse(
    val coord:Coordinate,
    val weather: List<Weather>,
    val main: WeatherInfo,
    val visibility:Int,
    val wind: Wind,
    val sys:SystemInfo,
    val timezone:Int
)
