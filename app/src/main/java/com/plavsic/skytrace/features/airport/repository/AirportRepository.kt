package com.plavsic.skytrace.features.airport.repository

import com.plavsic.skytrace.features.airport.data.local.entity.AirportWithCity
import com.plavsic.skytrace.features.airport.data.local.entity.FlightAirports

interface AirportRepository {
    suspend fun getFlightAirports(departureCode:String,arrivalCode:String):FlightAirports
    suspend fun getAirportWithCity(codeIataAirport:String):AirportWithCity?
}