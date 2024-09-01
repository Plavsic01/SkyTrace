package com.plavsic.skytrace.features.aircraft.repository.impl

import com.plavsic.skytrace.BuildConfig
import com.plavsic.skytrace.features.aircraft.data.local.dao.AircraftDAO
import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity
import com.plavsic.skytrace.features.aircraft.data.remote.AircraftService
import com.plavsic.skytrace.features.aircraft.mapper.toDomainModelList
import com.plavsic.skytrace.features.aircraft.repository.AircraftRepository
import com.plavsic.skytrace.utils.resource.UIState
import java.io.IOException
import javax.inject.Inject

class AircraftRepositoryImpl @Inject constructor(
    private val aircraftDAO: AircraftDAO,
    private val aircraftService: AircraftService
) : AircraftRepository{
    override suspend fun getAircrafts(): UIState<List<AircraftEntity>> {
        return try {
            var aircrafts = aircraftDAO.getAllAircrafts()
            if(aircrafts.isEmpty()){
                val response = aircraftService
                    .getAircrafts(BuildConfig.AVIATION_EDGE_API_KEY)
                println(response.code())
                if(response.isSuccessful){
                    val aircraftDTOList = response.body()
                    if(!aircraftDTOList.isNullOrEmpty()){
                        aircraftDAO.insertAll(aircraftDTOList.toDomainModelList())
                        aircrafts = aircraftDAO.getAllAircrafts()
                        UIState.Success(aircrafts)
                    }
                }else{
                    return when(response.code()){
                        404 -> UIState.Error.ServerError(response.code(),"Resource not found")
                        429 -> UIState.Error.ServerError(response.code(),"Too many requests")
                        500 -> UIState.Error.ServerError(response.code(),"Server error")
                        else -> UIState.Error.ServerError(response.code(),"Unknown server error")
                    }
                }
            }
            UIState.Success(aircrafts)
        }catch (e: IOException) {
            UIState.Error.NetworkError("Network error: ${e.message}")
        }catch (e:Exception){
            UIState.Error.UnknownError(e)
        }
    }
}