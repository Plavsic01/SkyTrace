package com.plavsic.skytrace.features.futureFlight.model

data class FutureFlightResponse(
    val weekday:String,
    val departure: FlightDirection,
    val arrival: FlightDirection,
    val aircraft: Aircraft,
    val airline: Airline,
    val flight: Flight,
    val codeshared:CodeShared?
)
