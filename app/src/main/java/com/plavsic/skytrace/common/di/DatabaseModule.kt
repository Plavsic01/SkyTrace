package com.plavsic.skytrace.common.di

import android.content.Context
import androidx.room.Room
import com.plavsic.skytrace.features.aircraft.data.local.AircraftDatabase
import com.plavsic.skytrace.features.aircraft.data.local.dao.AircraftDAO
import com.plavsic.skytrace.features.airport.data.local.AirportDatabase
import com.plavsic.skytrace.features.airport.data.local.dao.AirportDAO
import com.plavsic.skytrace.features.airport.data.local.dao.CityDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAirportDatabase(
        @ApplicationContext appContext: Context
    ): AirportDatabase {
        return Room.databaseBuilder(
            appContext,
            AirportDatabase::class.java,
            "airports-db"
        ).build()
    }

    @Provides
    fun provideAirportDAO(database: AirportDatabase): AirportDAO {
        return database.airportDAO()
    }

    @Provides
    fun provideCityDAO(database: AirportDatabase): CityDAO {
        return database.cityDAO()
    }


    @Provides
    @Singleton
    fun provideAircraftDatabase(
        @ApplicationContext appContext: Context
    ): AircraftDatabase {
//        return Room.inMemoryDatabaseBuilder(appContext,AircraftDatabase::class.java).build()
        return Room.databaseBuilder(
            appContext,
            AircraftDatabase::class.java,
            "aircraft-db"
        ).build()
    }

    @Provides
    fun provideAircraftDAO(database: AircraftDatabase): AircraftDAO {
        return database.aircraftDAO()
    }
}