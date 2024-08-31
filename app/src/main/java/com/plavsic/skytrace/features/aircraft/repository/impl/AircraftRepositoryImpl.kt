package com.plavsic.skytrace.features.aircraft.repository.impl

import com.plavsic.skytrace.BuildConfig
import com.plavsic.skytrace.features.aircraft.data.local.dao.AircraftDAO
import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity
import com.plavsic.skytrace.features.aircraft.data.remote.AircraftService
import com.plavsic.skytrace.features.aircraft.mapper.toDomainModelList
import com.plavsic.skytrace.features.aircraft.repository.AircraftRepository
import javax.inject.Inject

class AircraftRepositoryImpl @Inject constructor(
    private val aircraftDAO: AircraftDAO,
    private val aircraftService: AircraftService
) : AircraftRepository{
    override suspend fun getAircrafts(): List<AircraftEntity>? {
        return try {
            var aircrafts = aircraftDAO.getAllAircrafts()
            if(aircrafts.isEmpty()){
                val response = aircraftService
                    .getAircrafts(BuildConfig.AVIATION_EDGE_API_KEY)
                if(response.isSuccessful){
                    val aircraftDTOList = response.body()
                    println("LISTA $aircraftDTOList")
                    if(!aircraftDTOList.isNullOrEmpty()){
                        aircraftDAO.insertAll(aircraftDTOList.toDomainModelList())
                        aircrafts = aircraftDAO.getAllAircrafts()
                    }else{
                        return null
                    }
                }else{
                    return null
                }
            }
            aircrafts
        }catch (e:Exception){
            println(e.message)
            null
        }
    }
}