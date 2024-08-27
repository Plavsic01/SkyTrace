package com.plavsic.skytrace.features.futureFlights.model

data class FutureFlightResponse(
    val weekday:String,
    val departure: FlightDirection,
    val arrival: FlightDirection,
    val aircraft: Aircraft,
    val airline: Airline,
    val flight: Flight
)
