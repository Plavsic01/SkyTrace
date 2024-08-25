package com.plavsic.skytrace.features.airports.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plavsic.skytrace.features.airports.data.local.entity.CityEntity

@Dao
interface CityDAO {
    @Query("SELECT * FROM cities WHERE codeIataCity = :codeIataCity")
    suspend fun getCityByCode(codeIataCity: String): CityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity)
}