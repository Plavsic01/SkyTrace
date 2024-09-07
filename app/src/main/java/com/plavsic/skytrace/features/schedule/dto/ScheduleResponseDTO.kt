package com.plavsic.skytrace.features.schedule.dto

import com.plavsic.skytrace.features.map.dto.FlightDTO


data class ScheduleResponseDTO(
    val airline:AirlineDetailsDTO,
    val arrival:FlightDirectionDTO,
    val departure:FlightDirectionDTO,
    val flight: FlightDTO
)
