package com.plavsic.skytrace.common.di


import com.plavsic.skytrace.common.remoteService.FlightService
import com.plavsic.skytrace.common.repository.FlightTrackerRepository
import com.plavsic.skytrace.features.map.repository.FlightTrackerRepositoryImpl
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
    fun provideApiService():FlightService {
        return Retrofit.Builder()
            .baseUrl("https://aviation-edge.com/v2/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlightService::class.java)
    }



    @Provides
    fun provideFlightTrackerRepository(flightService:FlightService):FlightTrackerRepository{
        return FlightTrackerRepositoryImpl(flightService)
    }


}
