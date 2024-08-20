package com.plavsic.skytrace.features.map.mappers

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


fun FlightResponseDTO.toDomainModel():FlightResponse{
    return FlightResponse(
        aircraft = this.aircraft.toDomainModel(),
        airline = this.airline.toDomainModel(),
        arrival = this.arrival.toDomainModel(),
        departure = this.departure.toDomainModel(),
        flight = this.flight.toDomainModel(),
        geography = this.geography.toDomainModel(),
        speed = this.speed.toDomainModel(),
        status = this.status,
        system = this.system.toDomainModel()
    )
}

fun AircraftDTO.toDomainModel():Aircraft {
    return Aircraft(
        iataCode = this.iataCode,
    icaoCode = this.icaoCode,
    icao24 = this.icao24,
    regNumber = this.regNumber
    )
}


fun AirlineDTO.toDomainModel():Airline{
    return Airline(
        iataCode = this.iataCode,
        icaoCode = this.icaoCode
    )
}


fun ArrivalDTO.toDomainModel():Arrival{
    return Arrival(
        iataCode = this.iataCode,
        icaoCode = this.icaoCode
    )
}

fun DepartureDTO.toDomainModel():Departure{
    return Departure(
        iataCode = this.iataCode,
        icaoCode = this.icaoCode
    )
}

fun FlightDTO.toDomainModel():Flight{
    return Flight(
        iataNumber = this.iataNumber,
        icaoNumber = this.icaoNumber,
        number = this.number
    )
}

fun GeographyDTO.toDomainModel():Geography{
    return Geography(
        altitude = this.altitude,
        direction = this.direction,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun SpeedDTO.toDomainModel():Speed{
    return Speed(
        horizontal = this.horizontal,
        isGround = this.isGround
    )
}

fun SystemDTO.toDomainModel():System{
    return System(
        updated = this.updated
    )
}


