package com.plavsic.skytrace.features.futureFlight.repository

import com.plavsic.skytrace.features.futureFlight.model.FutureFlightResponse
import com.plavsic.skytrace.utils.resource.UIState


interface FutureFlightRepository {
    suspend fun getFutureFlights(
        iataCode:String,
        type:String,
        date:String,
        airlineIata:String?,
        flightNum:String?
    ):UIState<List<FutureFlightResponse>>
}