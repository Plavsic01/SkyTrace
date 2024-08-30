package com.plavsic.skytrace.features.weather.repository.impl

import com.plavsic.skytrace.BuildConfig
import com.plavsic.skytrace.common.remoteService.WeatherService
import com.plavsic.skytrace.features.weather.mapper.toDomainModel
import com.plavsic.skytrace.features.weather.model.WeatherResponse
import com.plavsic.skytrace.features.weather.repository.WeatherRepository
import com.plavsic.skytrace.utils.resource.UIState
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
):WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double,units:String): UIState<WeatherResponse> {
        return try {
            val response = weatherService
                .getWeather(BuildConfig.OPEN_WEATHER_MAP_API_KEY, lat, lon,units)

            if (response.isSuccessful) {
                response.body()
                UIState.Success(response.body()!!.toDomainModel())
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
