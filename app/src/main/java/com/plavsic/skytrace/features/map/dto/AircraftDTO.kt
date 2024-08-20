package com.plavsic.skytrace.features.map.dto

import com.plavsic.skytrace.features.map.model.Aircraft

data class AircraftDTO(
    val iataCode:String,
    val icaoCode:String,
    val icao24:String,
    val regNumber:String
)


