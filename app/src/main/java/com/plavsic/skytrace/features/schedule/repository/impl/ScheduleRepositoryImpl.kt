package com.plavsic.skytrace.features.schedule.repository.impl

import com.plavsic.skytrace.BuildConfig
import com.plavsic.skytrace.common.remoteService.FlightService
import com.plavsic.skytrace.features.schedule.mapper.toDomainModelList
import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import com.plavsic.skytrace.features.schedule.repository.ScheduleRepository
import com.plavsic.skytrace.utils.resource.UIState
import java.io.IOException

class ScheduleRepositoryImpl(
    private val flightService: FlightService
) : ScheduleRepository{

    override suspend fun getSchedules(flightNum:String,flightIata:String,status:String): UIState<List<ScheduleResponse>> {
        return try {
            val response = flightService
                .getSchedules(BuildConfig.AVIATION_EDGE_API_KEY,flightNum,flightIata, status = status)
            if (response.isSuccessful) {
                val schedules = response.body() ?: emptyList()
                UIState.Success(schedules.toDomainModelList())
            }else{
                when(response.code()){
                    404 -> UIState.Error.ServerError(response.code(),"Resource not found")
                    429 -> UIState.Error.ServerError(response.code(),"Too many requests")
                    500 -> UIState.Error.ServerError(response.code(),"Server error")
                    else -> UIState.Error.ServerError(response.code(),"Unknown server error")
                }
            }
        }catch (e:IOException){
            UIState.Error.NetworkError("Network error: ${e.message}")
        }catch (e:Exception){
            UIState.Error.UnknownError(e)
        }


    }

}