package com.plavsic.skytrace.common.di


import com.google.gson.GsonBuilder
import com.plavsic.skytrace.common.remoteService.FlightService
import com.plavsic.skytrace.features.map.repository.FlightTrackerRepository
import com.plavsic.skytrace.features.map.repository.impl.FlightTrackerRepositoryImpl
import com.plavsic.skytrace.features.schedule.dto.AirlineDetailsDTO
import com.plavsic.skytrace.features.schedule.repository.ScheduleRepository
import com.plavsic.skytrace.features.schedule.repository.impl.ScheduleRepositoryImpl
import com.plavsic.skytrace.utils.deserializer.AirlineDetailsDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    val gson = GsonBuilder()
        .registerTypeAdapter(AirlineDetailsDTO::class.java,AirlineDetailsDeserializer())
        .create()

    @Provides
    fun provideApiService():FlightService {
        return Retrofit.Builder()
            .baseUrl("https://aviation-edge.com/v2/public/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(FlightService::class.java)
    }


    @Provides
    fun provideFlightTrackerRepository(flightService:FlightService): FlightTrackerRepository {
        return FlightTrackerRepositoryImpl(flightService)
    }

    @Provides
    fun provideScheduleRepository(flightService:FlightService): ScheduleRepository {
        return ScheduleRepositoryImpl(flightService)
    }


}
