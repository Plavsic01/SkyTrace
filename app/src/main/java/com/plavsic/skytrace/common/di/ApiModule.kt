package com.plavsic.skytrace.common.di


import com.google.gson.GsonBuilder
import com.plavsic.skytrace.common.remoteService.FlightService
import com.plavsic.skytrace.features.airport.data.local.dao.AirportDAO
import com.plavsic.skytrace.features.airport.data.local.dao.CityDAO
import com.plavsic.skytrace.features.airport.data.remote.AirportService
import com.plavsic.skytrace.features.airport.data.remote.CityService
import com.plavsic.skytrace.features.airport.repository.AirportRepository
import com.plavsic.skytrace.features.airport.repository.CityRepository
import com.plavsic.skytrace.features.airport.repository.impl.AirportRepositoryImpl
import com.plavsic.skytrace.features.airport.repository.impl.CityRepositoryImpl
import com.plavsic.skytrace.features.futureFlight.repository.FutureFlightRepository
import com.plavsic.skytrace.features.futureFlight.repository.impl.FutureFlightRepositoryImpl
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
        .registerTypeAdapter(AirlineDetailsDTO::class.java, AirlineDetailsDeserializer())
        .create()

    @Provides
    fun provideFlightService():FlightService {
        return Retrofit.Builder()
            .baseUrl("https://aviation-edge.com/v2/public/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(FlightService::class.java)
    }

    @Provides
    fun provideAirportService():AirportService {
        return Retrofit.Builder()
            .baseUrl("https://aviation-edge.com/v2/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AirportService::class.java)
    }

    @Provides
    fun provideCityService():CityService {
        return Retrofit.Builder()
            .baseUrl("https://aviation-edge.com/v2/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityService::class.java)
    }


    @Provides
    fun provideFlightTrackerRepository(flightService:FlightService): FlightTrackerRepository {
        return FlightTrackerRepositoryImpl(flightService)
    }

    @Provides
    fun provideScheduleRepository(flightService:FlightService): ScheduleRepository {
        return ScheduleRepositoryImpl(flightService)
    }

    @Provides
    fun provideFutureFlightRepository(flightService:FlightService): FutureFlightRepository {
        return FutureFlightRepositoryImpl(flightService)
    }

    @Provides
    fun provideAirportRepository(
        airportDAO: AirportDAO,
        cityDAO: CityDAO,
        airportService: AirportService,
        cityService: CityService
    ): AirportRepository {
        return AirportRepositoryImpl(airportDAO,cityDAO,airportService,cityService)
    }

    @Provides
    fun provideCityRepository(
        cityDAO: CityDAO,
        cityService: CityService
    ): CityRepository {
        return CityRepositoryImpl(cityDAO,cityService)
    }


}
