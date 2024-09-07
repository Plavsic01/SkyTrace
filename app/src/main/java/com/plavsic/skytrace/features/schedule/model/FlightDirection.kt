package com.plavsic.skytrace.features.schedule.model

data class FlightDirection(
    val delay:String?,
    val terminal:String?,
    val scheduledTime:String?,
    val gate:String?,
    val baggage:String?,
    val iataCode:String
)
