package com.plavsic.skytrace.features.map.mapper

import com.plavsic.skytrace.features.map.dto.AircraftDTO
import com.plavsic.skytrace.features.map.dto.AirlineDTO
import com.plavsic.skytrace.features.map.dto.ArrivalDTO
import com.plavsic.skytrace.features.map.dto.DepartureDTO
import com.plavsic.skytrace.features.map.dto.FlightDTO
import com.plavsic.skytrace.features.map.dto.FlightResponseDTO
import com.plavsic.skytrace.features.map.dto.GeographyDTO
import com.plavsic.skytrace.features.map.dto.SpeedDTO
import com.plavsic.skytrace.features.map.dto.SystemDTO
import com.plavsic.skytrace.features.map.model.Aircraft
import com.plavsic.skytrace.features.map.model.Airline
import com.plavsic.skytrace.features.map.model.Arrival
import com.plavsic.skytrace.features.map.model.Departure
import com.plavsic.skytrace.features.map.model.Flight
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.map.model.Geography
import com.plavsic.skytrace.features.map.model.Speed
import com.plavsic.skytrace.features.map.model.System

fun FlightResponse.toDto(): FlightResponseDTO {
    return FlightResponseDTO(
        aircraft = this.aircraft.toDto(),
        airline = this.airline.toDto(),
        arrival = this.arrival.toDto(),
        departure = this.departure.toDto(),
        flight = this.flight.toDto(),
        geography = this.geography.toDto(),
        speed = this.speed.toDto(),
        status = this.status,
        system = this.system.toDto()
    )
}


fun List<FlightResponse>.toDtoList():List<FlightResponseDTO>{
    return this.map { it.toDto() }
}


fun Aircraft.toDto(): AircraftDTO {
    return AircraftDTO(
        iataCode = this.iataCode,
        icaoCode = this.icaoCode,
        icao24 = this.icao24,
        regNumber = this.regNumber
    )
}


fun Airline.toDto(): AirlineDTO {
    return AirlineDTO(
        iataCode = this.iataCode,
        icaoCode = this.icaoCode
    )
}


fun Arrival.toDto(): ArrivalDTO {
    return ArrivalDTO(
        iataCode = this.iataCode,
        icaoCode = this.icaoCode
    )
}

fun Departure.toDto(): DepartureDTO {
    return DepartureDTO(
        iataCode = this.iataCode,
        icaoCode = this.icaoCode
    )
}

fun Flight.toDto(): FlightDTO {
    return FlightDTO(
        iataNumber = this.iataNumber,
        icaoNumber = this.icaoNumber,
        number = this.number
    )
}

fun Geography.toDto(): GeographyDTO {
    return GeographyDTO(
        altitude = this.altitude,
        direction = this.direction,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun Speed.toDto(): SpeedDTO {
    return SpeedDTO(
        horizontal = this.horizontal,
        isGround = this.isGround
    )
}

fun System.toDto(): SystemDTO {
    return SystemDTO(
        updated = this.updated
    )
}