package com.plavsic.skytrace.features.futureFlight.dto

data class FutureFlightResponseDTO(
    val weekday:String,
    val departure: FlightDirectionDTO,
    val arrival: FlightDirectionDTO,
    val aircraft: AircraftDTO,
    val airline: AirlineDTO,
    val flight: FlightDTO,
    val codeshared: CodeSharedDTO?
)
