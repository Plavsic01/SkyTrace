package com.plavsic.skytrace.common.di

import android.content.Context
import androidx.room.Room
import com.plavsic.skytrace.features.airport.data.local.AppDatabase
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
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "airports-db"
        ).build()
    }

    @Provides
    fun provideAirportDao(database: AppDatabase): AirportDAO {
        return database.airportDAO()
    }

    @Provides
    fun provideCityDao(database: AppDatabase): CityDAO {
        return database.cityDAO()
    }
}