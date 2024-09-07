package com.plavsic.skytrace.features.futureFlight.dto

data class FlightDirectionDTO(
    val iataCode:String,
    val terminal:String,
    val gate:String,
    val scheduledTime:String
)
