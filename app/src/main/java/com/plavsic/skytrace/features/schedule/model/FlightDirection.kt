package com.plavsic.skytrace.features.schedule.model

data class FlightDirection(
    val actualRunway:String?,
    val actualTime:String?,
    val delay:String?,
    val terminal:String?,
    val scheduledTime:String?,
    val gate:String?,
    val iataCode:String,
    val icaoCode:String
)
