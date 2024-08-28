package com.plavsic.skytrace.features.schedule.mapper


import com.plavsic.skytrace.features.futureFlight.mapper.toDomainModel
import com.plavsic.skytrace.features.map.mapper.toDomainModel
import com.plavsic.skytrace.features.schedule.dto.AirlineDetailsDTO
import com.plavsic.skytrace.features.schedule.dto.FlightDirectionDTO
import com.plavsic.skytrace.features.schedule.dto.ScheduleResponseDTO
import com.plavsic.skytrace.features.schedule.model.AirlineDetails
import com.plavsic.skytrace.features.schedule.model.FlightDirection
import com.plavsic.skytrace.features.schedule.model.ScheduleResponse


fun ScheduleResponseDTO.toDomainModel():ScheduleResponse{
    return ScheduleResponse(
        airline = this.airline.toDomainModel(),
        arrival = this.arrival.toDomainModel(),
        departure = this.departure.toDomainModel(),
        flight = this.flight.toDomainModel()
    )
}

fun List<ScheduleResponseDTO>.toDomainModelList():List<ScheduleResponse>{
    return this.map { it.toDomainModel() }
}


fun AirlineDetailsDTO.toDomainModel():AirlineDetails{
    return AirlineDetails(
        airline = this.airline.toDomainModel(),
        name = this.name
    )
}


fun FlightDirectionDTO.toDomainModel():FlightDirection{
    return FlightDirection(
        delay = this.delay,
        terminal = this.terminal,
        scheduledTime = this.scheduledTime,
        gate = this.gate,
        baggage = this.baggage,
        iataCode = this.iataCode

    )
}

