package com.plavsic.skytrace.features.airports.repository

import com.plavsic.skytrace.features.airports.data.local.entity.AirportWithCity
import com.plavsic.skytrace.features.airports.data.local.entity.FlightAirports

interface AirportRepository {
    suspend fun getFlightAirports(departureCode:String,arrivalCode:String):FlightAirports
    suspend fun getAirportWithCity(codeIataAirport:String):AirportWithCity?
}