package com.plavsic.skytrace.features.map.repository.impl

import com.plavsic.skytrace.BuildConfig
import com.plavsic.skytrace.common.remoteService.FlightService
import com.plavsic.skytrace.features.map.repository.FlightTrackerRepository
import com.plavsic.skytrace.features.map.mapper.toDomainModelList
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.utils.resource.UIState
import okio.IOException

class FlightTrackerRepositoryImpl(
    private val flightService: FlightService
) : FlightTrackerRepository {

    override suspend fun getFlights(limit: Int): UIState<List<FlightResponse>> {
        return try {
            val response = flightService
                .getFlights(BuildConfig.AVIATION_EDGE_API_KEY, limit = limit)

            if (response.isSuccessful) {
                val flights = response.body() ?: emptyList()
                UIState.Success(flights.toDomainModelList())
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