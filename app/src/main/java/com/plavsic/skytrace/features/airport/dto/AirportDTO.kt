package com.plavsic.skytrace.features.airport.dto

data class AirportDTO(
    val codeIataAirport: String,
    val GMT: String,
    val codeIataCity: String,  // Ovo je referenca na CityEntity
    val latitudeAirport: Double,
    val longitudeAirport: Double,
    val nameAirport: String,
    val nameCountry: String,
    val phone: String?
)
