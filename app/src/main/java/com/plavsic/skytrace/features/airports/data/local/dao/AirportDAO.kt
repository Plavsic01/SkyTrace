package com.plavsic.skytrace.features.airports.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.plavsic.skytrace.features.airports.data.local.entity.AirportEntity
import com.plavsic.skytrace.features.airports.data.local.entity.AirportWithCity

@Dao
interface AirportDAO {

    @Transaction
    @Query("SELECT * FROM airports WHERE codeIataAirport = :codeIataAirport")
    suspend fun getAirportWithCityByCode(codeIataAirport: String): AirportWithCity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAirport(airport: AirportEntity)
}