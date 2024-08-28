package com.plavsic.skytrace.features.airport.mapper

import com.plavsic.skytrace.features.airport.data.local.entity.AirportEntity
import com.plavsic.skytrace.features.airport.data.local.entity.CityEntity
import com.plavsic.skytrace.features.airport.dto.AirportDTO
import com.plavsic.skytrace.features.airport.dto.CityDTO

fun AirportDTO.toEntity(): AirportEntity {
    return AirportEntity(
        codeIataAirport = this.codeIataAirport,
        GMT = this.GMT,
        codeIataCity = this.codeIataCity,
        latitudeAirport = this.latitudeAirport,
        longitudeAirport = this.longitudeAirport,
        nameAirport = this.nameAirport,
        nameCountry = this.nameCountry,
        phone = this.phone
    )
}

fun CityDTO.toEntity(): CityEntity {
    return CityEntity(
        codeIataCity = this.codeIataCity,
        GMT = this.GMT,
        latitudeCity = this.latitudeCity,
        longitudeCity = this.longitudeCity,
        nameCity = this.nameCity
    )
}
