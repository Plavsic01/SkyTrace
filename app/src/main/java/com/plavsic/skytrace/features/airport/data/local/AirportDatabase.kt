package com.plavsic.skytrace.features.airport.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plavsic.skytrace.features.airport.data.local.dao.AirportDAO
import com.plavsic.skytrace.features.airport.data.local.dao.CityDAO
import com.plavsic.skytrace.features.airport.data.local.entity.AirportEntity
import com.plavsic.skytrace.features.airport.data.local.entity.CityEntity

@Database(entities = [AirportEntity::class,CityEntity::class], version = 2)
abstract class AirportDatabase : RoomDatabase() {
    abstract fun airportDAO() : AirportDAO
    abstract fun cityDAO() : CityDAO
}