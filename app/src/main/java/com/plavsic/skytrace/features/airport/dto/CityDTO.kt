package com.plavsic.skytrace.features.airport.dto

data class CityDTO(
    val codeIataCity: String,
    val GMT: String,
    val latitudeCity: Double,
    val longitudeCity: Double,
    val nameCity: String
)
