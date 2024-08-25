package com.plavsic.skytrace.features.airports.repository

import com.plavsic.skytrace.features.airports.data.local.entity.CityEntity

interface CityRepository {
    suspend fun getCity(codeIataCity: String): CityEntity?
}
