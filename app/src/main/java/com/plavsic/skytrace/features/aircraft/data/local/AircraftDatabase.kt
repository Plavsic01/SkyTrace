package com.plavsic.skytrace.features.aircraft.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plavsic.skytrace.features.aircraft.data.local.dao.AircraftDAO
import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity

@Database(entities = [AircraftEntity::class], version = 3)
abstract class AircraftDatabase : RoomDatabase() {
    abstract fun aircraftDAO() : AircraftDAO
}
