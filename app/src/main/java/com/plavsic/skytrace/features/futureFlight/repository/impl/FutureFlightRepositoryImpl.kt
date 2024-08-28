package com.plavsic.skytrace.features.futureFlight.repository.impl

import com.plavsic.skytrace.BuildConfig
import com.plavsic.skytrace.common.remoteService.FlightService
import com.plavsic.skytrace.features.futureFlight.mapper.toDomainModelList
import com.plavsic.skytrace.features.futureFlight.model.FutureFlightResponse
import com.plavsic.skytrace.features.futureFlight.repository.FutureFlightRepository
import com.plavsic.skytrace.utils.resource.UIState
import java.io.IOException

class FutureFlightRepositoryImpl(
    private val flightService: FlightService
):FutureFlightRepository {

    override suspend fun getFutureFlights(
        iataCode: String,
        type: String,
        date: String,
        airlineIata: String?,
        flightNum:String?
    ): UIState<List<FutureFlightResponse>> {
        return try {
            val response = flightService
                .getFutureFlights(BuildConfig.AVIATION_EDGE_API_KEY,iataCode, type, date, airlineIata,flightNum)

            if (response.isSuccessful) {
                val futureFlights = response.body() ?: emptyList()
                UIState.Success(futureFlights.toDomainModelList())
            }else{
                when(response.code()){
                    404 -> UIState.Error.ServerError(response.code(),"Resource not found")
                    429 -> UIState.Error.ServerError(response.code(),"Too many requests")
                    500 -> UIState.Error.ServerError(response.code(),"Server error")
                    else -> UIState.Error.ServerError(response.code(),"Unknown server error")
                }
            }
        }catch (e: IOException){
            UIState.Error.NetworkError("Network error: ${e.message}")
        }catch (e:Exception){
            UIState.Error.UnknownError(e)
        }
    }
}