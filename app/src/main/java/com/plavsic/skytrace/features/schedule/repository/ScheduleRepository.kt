package com.plavsic.skytrace.features.schedule.repository

import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import com.plavsic.skytrace.utils.resource.UIState
import retrofit2.http.Query

interface ScheduleRepository {
    suspend fun getSchedules(
        flightNum:String,
        flightIata:String,
        status:String
    ): UIState<List<ScheduleResponse>>
}