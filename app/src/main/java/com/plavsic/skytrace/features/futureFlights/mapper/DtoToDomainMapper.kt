package com.plavsic.skytrace.features.futureFlights.mapper

import com.plavsic.skytrace.features.futureFlights.dto.AircraftDTO
import com.plavsic.skytrace.features.futureFlights.dto.AirlineDTO
import com.plavsic.skytrace.features.futureFlights.dto.FlightDirectionDTO
import com.plavsic.skytrace.features.futureFlights.model.Aircraft
import com.plavsic.skytrace.features.futureFlights.model.Airline
import com.plavsic.skytrace.features.futureFlights.model.FlightDirection

fun AircraftDTO.toDomainModel(): Aircraft {
    return Aircraft(
        modelCode = this.modelCode,
        modelText = this.modelText
    )
}


fun AirlineDTO.toDomainModel(): Airline {
    return Airline(
        name = this.name,
        iataCode = this.iataCode
    )
}


fun FlightDirectionDTO.toDomainModel(): FlightDirection {
    return FlightDirection(
        iataCode = this.iataCode,
        terminal = this.terminal,
        gate = this.gate,
        scheduledTime = this.scheduledTime
    )
}
