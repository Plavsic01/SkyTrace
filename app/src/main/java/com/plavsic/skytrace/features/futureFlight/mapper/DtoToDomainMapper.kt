package com.plavsic.skytrace.features.futureFlight.mapper

import com.plavsic.skytrace.features.futureFlight.dto.AircraftDTO
import com.plavsic.skytrace.features.futureFlight.dto.AirlineDTO
import com.plavsic.skytrace.features.futureFlight.dto.CodeSharedDTO
import com.plavsic.skytrace.features.futureFlight.dto.FlightDTO
import com.plavsic.skytrace.features.futureFlight.dto.FlightDirectionDTO
import com.plavsic.skytrace.features.futureFlight.dto.FutureFlightResponseDTO
import com.plavsic.skytrace.features.futureFlight.model.Aircraft
import com.plavsic.skytrace.features.futureFlight.model.Airline
import com.plavsic.skytrace.features.futureFlight.model.CodeShared
import com.plavsic.skytrace.features.futureFlight.model.Flight
import com.plavsic.skytrace.features.futureFlight.model.FlightDirection
import com.plavsic.skytrace.features.futureFlight.model.FutureFlightResponse


fun FutureFlightResponseDTO.toDomainModel():FutureFlightResponse{
    return FutureFlightResponse(
        weekday = this.weekday,
        departure = this.departure.toDomainModel(),
        arrival = this.arrival.toDomainModel(),
        aircraft = this.aircraft.toDomainModel(),
        airline = this.airline.toDomainModel(),
        flight = this.flight.toDomainModel(),
        codeshared = this.codeshared?.toDomainModel()
    )
}

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

fun List<FutureFlightResponseDTO>.toDomainModelList():List<FutureFlightResponse>{
    return this.map { it.toDomainModel() }
}


fun FlightDirectionDTO.toDomainModel(): FlightDirection {
    return FlightDirection(
        iataCode = this.iataCode,
        terminal = this.terminal,
        gate = this.gate,
        scheduledTime = this.scheduledTime
    )
}

fun FlightDTO.toDomainModel(): Flight {
    return Flight(
        iataNumber = this.iataNumber,
        number = this.number
    )
}

fun CodeSharedDTO.toDomainModel():CodeShared{
    return CodeShared(
        airline = this.airline.toDomainModel(),
        flight = this.flight.toDomainModel()
    )
}
