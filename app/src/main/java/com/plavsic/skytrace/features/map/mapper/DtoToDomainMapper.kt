package com.plavsic.skytrace.features.map.mapper

import com.plavsic.skytrace.features.futureFlights.dto.AircraftDTO
import com.plavsic.skytrace.features.futureFlights.dto.AirlineDTO
import com.plavsic.skytrace.features.futureFlights.dto.FlightDTO
import com.plavsic.skytrace.features.map.dto.FlightResponseDTO
import com.plavsic.skytrace.features.map.dto.GeographyDTO
import com.plavsic.skytrace.features.map.dto.SpeedDTO
import com.plavsic.skytrace.features.map.dto.SystemDTO
import com.plavsic.skytrace.features.futureFlights.model.Aircraft
import com.plavsic.skytrace.features.futureFlights.model.Airline
import com.plavsic.skytrace.features.map.model.Flight
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.map.model.Geography
import com.plavsic.skytrace.features.map.model.Speed
import com.plavsic.skytrace.features.map.model.System


fun FlightResponseDTO.toDomainModel():FlightResponse{
    return FlightResponse(
//        aircraft = this.aircraft.toDomainModel(),
//        airline = this.airline.toDomainModel(),
//        arrival = this.arrival.toDomainModel(),
//        departure = this.departure.toDomainModel(),
        flight = this.flight.toDomainModel(),
        geography = this.geography.toDomainModel(),
        speed = this.speed.toDomainModel(),
//        status = this.status,
//        system = this.system.toDomainModel()
    )
}

fun List<FlightResponseDTO>.toDomainModelList():List<FlightResponse>{
    return this.map { it.toDomainModel() }
}

fun FlightDTO.toDomainModel():Flight{
    return Flight(
        iataNumber = this.iataNumber,
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


