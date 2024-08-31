package com.plavsic.skytrace.features.aircraft.mapper

import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity
import com.plavsic.skytrace.features.aircraft.dto.AircraftDTO

fun AircraftDTO.toDomainModel():AircraftEntity{
    return AircraftEntity(
        airplaneId = this.airplaneId,
        airplaneIataType = this.airplaneIataType ?: "N/A",
        enginesCount = this.enginesCount ?: "N/A",
        enginesType = this.enginesType ?: "N/A",
        firstFlight = this.firstFlight ?: "N/A",
        modelCode = this.modelCode ?: "N/A",
        numberRegistration = this.numberRegistration ?: "N/A",
        planeAge = this.planeAge ?: "N/A",
        planeModel = this.planeModel ?: "N/A",
        planeOwner = this.planeOwner ?: "N/A",
        planeStatus = this.planeStatus ?: "N/A",
        productionLine = this.productionLine ?: "N/A",
        registrationDate = this.registrationDate ?: "N/A"
    )
}


fun List<AircraftDTO>.toDomainModelList():List<AircraftEntity>{
    return this.map { it.toDomainModel() }
}

