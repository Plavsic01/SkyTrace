package com.plavsic.skytrace.features.schedule.dto

import com.plavsic.skytrace.features.futureFlights.dto.FlightDTO


data class ScheduleResponseDTO(
    val airline:AirlineDetailsDTO,
    val arrival:FlightDirectionDTO,
    val departure:FlightDirectionDTO,
    val flight: FlightDTO
)
