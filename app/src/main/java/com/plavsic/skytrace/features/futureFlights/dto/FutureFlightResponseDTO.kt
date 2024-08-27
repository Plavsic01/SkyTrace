package com.plavsic.skytrace.features.futureFlights.dto

import com.plavsic.skytrace.features.futureFlights.model.Aircraft
import com.plavsic.skytrace.features.futureFlights.model.Airline
import com.plavsic.skytrace.features.futureFlights.model.Flight

data class FutureFlightResponseDTO(
    val weekday:String,
    val departure: FlightDirectionDTO,
    val arrival: FlightDirectionDTO,
    val aircraft: Aircraft,
    val airline: Airline,
    val flight: Flight
)
