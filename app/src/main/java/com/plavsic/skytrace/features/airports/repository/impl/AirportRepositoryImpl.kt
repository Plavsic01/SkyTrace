package com.plavsic.skytrace.features.airports.repository.impl

import com.plavsic.skytrace.BuildConfig
import com.plavsic.skytrace.features.airports.data.local.dao.AirportDAO
import com.plavsic.skytrace.features.airports.data.local.dao.CityDAO
import com.plavsic.skytrace.features.airports.data.local.entity.AirportWithCity
import com.plavsic.skytrace.features.airports.data.local.entity.FlightAirports
import com.plavsic.skytrace.features.airports.data.remote.AirportService
import com.plavsic.skytrace.features.airports.data.remote.CityService
import com.plavsic.skytrace.features.airports.mapper.toEntity
import com.plavsic.skytrace.features.airports.repository.AirportRepository
import javax.inject.Inject

class AirportRepositoryImpl @Inject constructor(
    private val airportDAO: AirportDAO,
    private val cityDAO: CityDAO,
    private val airportService: AirportService,
    private val cityService: CityService
):AirportRepository {

    private val api_key = BuildConfig.AVIATION_EDGE_API_KEY


    override suspend fun getFlightAirports(
        departureCode: String,
        arrivalCode: String
    ): FlightAirports {
        val departureAirport = getAirportWithCity(departureCode)
        val arrivalAirport = getAirportWithCity(arrivalCode)
        return FlightAirports(departure = departureAirport, arrival = arrivalAirport)
    }

    override suspend fun getAirportWithCity(codeIataAirport: String): AirportWithCity? {
        return try {
            var airportWithCity = airportDAO.getAirportWithCityByCode(codeIataAirport)
            if (airportWithCity == null) {
                // Fetching airport via API (returns Response<List<AirportDTO>>)
                val response = airportService.getAirportByCode(api_key,codeIataAirport)

                if (response.isSuccessful) {
                    val airportDtoList = response.body()
                    if (!airportDtoList.isNullOrEmpty()) {
                        val airportDTO = airportDtoList[0]
                        val airport = airportDTO.toEntity()

                        // Checking and fetching city if necessary
                        var city = cityDAO.getCityByCode(airport.codeIataCity)
                        if (city == null) {
                            val cityResponse = cityService.getCityByCode(api_key,airport.codeIataCity)
                            if (cityResponse.isSuccessful) {
                                val cityDTOList = cityResponse.body()
                                if (!cityDTOList.isNullOrEmpty()) {
                                    val cityDTO = cityDTOList[0]
                                    city = cityDTO.toEntity()
                                    cityDAO.insertCity(city)
                                }
                            }
                        }
                        airportDAO.insertAirport(airport)

                        // After both entities are in db, getting object again with city
                        airportWithCity = airportDAO.getAirportWithCityByCode(codeIataAirport)
                    }
                }
                else { // if response is not successful -> MAYBE IMPLEMENT
                    return null
                }
            }
            airportWithCity
        } catch (e: Exception) {
            null
        }
    }
}
