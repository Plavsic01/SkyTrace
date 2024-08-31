package com.plavsic.skytrace.features.aircraft.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity

@Dao
interface AircraftDAO {
    @Query("SELECT * FROM aircrafts")
    suspend fun getAllAircrafts(): List<AircraftEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAircraft(aircraft: AircraftEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(aircrafts: List<AircraftEntity>)

}