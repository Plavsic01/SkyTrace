package com.plavsic.skytrace.features.schedule.model

import com.plavsic.skytrace.features.map.model.Flight


data class ScheduleResponse(
    val airline:AirlineDetails,
    val arrival:FlightDirection,
    val departure:FlightDirection,
    val flight: Flight
)
