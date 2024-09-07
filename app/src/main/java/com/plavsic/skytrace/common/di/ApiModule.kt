package com.plavsic.skytrace.common.di


import com.plavsic.skytrace.common.remoteService.FlightService
import com.plavsic.skytrace.common.remoteService.WeatherService
import com.plavsic.skytrace.features.aircraft.data.local.dao.AircraftDAO
import com.plavsic.skytrace.features.aircraft.data.remote.AircraftService
import com.plavsic.skytrace.features.aircraft.repository.AircraftRepository
import com.plavsic.skytrace.features.aircraft.repository.impl.AircraftRepositoryImpl
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
import com.plavsic.skytrace.features.schedule.repository.ScheduleRepository
import com.plavsic.skytrace.features.schedule.repository.impl.ScheduleRepositoryImpl
import com.plavsic.skytrace.features.weather.repository.WeatherRepository
import com.plavsic.skytrace.features.weather.repository.impl.WeatherRepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideFlightService():FlightService {
        return Retrofit.Builder()
            .baseUrl("https://aviation-edge.com/v2/public/")
            .addConverterFactory(GsonConverterFactory.create())
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
    fun provideAircraftService():AircraftService {
        return Retrofit.Builder()
            .baseUrl("https://aviation-edge.com/v2/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AircraftService::class.java)
    }

    @Provides
    fun provideWeatherService():WeatherService {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
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

    @Provides
    fun provideWeatherRepository(weatherService: WeatherService): WeatherRepository {
        return WeatherRepositoryImpl(weatherService)
    }

    @Provides
    fun provideAircraftRepository(
        aircraftDAO: AircraftDAO,
        aircraftService: AircraftService
    ): AircraftRepository {
        return AircraftRepositoryImpl(aircraftDAO,aircraftService)
    }

}
