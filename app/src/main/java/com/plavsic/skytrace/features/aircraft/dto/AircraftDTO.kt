package com.plavsic.skytrace.features.aircraft.dto

data class AircraftDTO(
    val airplaneId: Int,
    val airplaneIataType: String?,
    val enginesCount: String?,
    val enginesType: String?,
    val firstFlight: String?,
    val modelCode: String?,
    val numberRegistration: String?,
    val planeAge: String?,
    val planeModel: String?,
    val planeOwner: String?,
    val planeStatus: String?,
    val productionLine: String?,
    val registrationDate: String?
)
