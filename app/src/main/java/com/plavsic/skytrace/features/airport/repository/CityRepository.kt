package com.plavsic.skytrace.features.airport.repository

import com.plavsic.skytrace.features.airport.data.local.entity.CityEntity

interface CityRepository {
    suspend fun getCity(codeIataCity: String): CityEntity?
}
