package com.plavsic.skytrace.features.schedule.dto


data class FlightDirectionDTO(
    val delay:String?,
    val terminal:String?,
    val scheduledTime:String?,
    val gate:String?,
    val baggage:String?,
    val iataCode:String
)
