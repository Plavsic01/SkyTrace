package com.plavsic.skytrace.features.futureFlights.dto

data class FlightDirectionDTO(
    val iataCode:String,
    val terminal:String,
    val gate:String,
    val scheduledTime:String
)
