package com.plavsic.skytrace.features.airport.repository.impl

import com.plavsic.skytrace.BuildConfig
import com.plavsic.skytrace.features.airport.data.local.dao.CityDAO
import com.plavsic.skytrace.features.airport.data.local.entity.CityEntity
import com.plavsic.skytrace.features.airport.data.remote.CityService
import com.plavsic.skytrace.features.airport.mapper.toEntity
import com.plavsic.skytrace.features.airport.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cityDAO: CityDAO,
    private val cityService: CityService
):CityRepository{

    override suspend fun getCity(codeIataCity: String): CityEntity? {
        return try {
            var city = cityDAO.getCityByCode(codeIataCity)
            if (city == null) {
                // Fetching city via API (returns Response<List<CityDTO>>)
                val response = cityService.getCityByCode(BuildConfig.AVIATION_EDGE_API_KEY,codeIataCity)

                if (response.isSuccessful) {
                    val cityDTOList = response.body()
                    if (!cityDTOList.isNullOrEmpty()) {
                        val cityDTO = cityDTOList[0]
                        city = cityDTO.toEntity()
                        cityDAO.insertCity(city)
                    } else {
                        return null  // City data not found
                    }
                } else {
                    return null  // API Error
                }
            }
            city
        } catch (e: Exception) {
            null  // Error while fetching or working with data
        }
    }
}