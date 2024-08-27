package com.plavsic.skytrace.features.futureFlights.model

data class FlightDirection(
    val iataCode:String,
    val terminal:String,
    val gate:String,
    val scheduledTime:String
)
