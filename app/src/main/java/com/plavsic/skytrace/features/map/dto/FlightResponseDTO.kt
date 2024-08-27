package com.plavsic.skytrace.features.map.dto

import com.plavsic.skytrace.features.futureFlights.dto.FlightDTO


data class FlightResponseDTO(
    val flight: FlightDTO,
    val geography: GeographyDTO,
    val speed: SpeedDTO
)
