package com.plavsic.skytrace.features.map.dto


data class FlightResponseDTO(
    val flight: FlightDTO,
    val geography: GeographyDTO,
    val speed: SpeedDTO
)
