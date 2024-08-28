package com.plavsic.skytrace.features.schedule.model

import com.plavsic.skytrace.features.futureFlight.model.Airline

data class AirlineDetails(
    val airline: Airline,
    val name:String
)