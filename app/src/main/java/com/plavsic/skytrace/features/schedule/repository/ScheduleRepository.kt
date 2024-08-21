package com.plavsic.skytrace.features.schedule.repository

import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import com.plavsic.skytrace.utils.resource.UIState

interface ScheduleRepository {
    suspend fun getSchedules(
        limit:Int
    ): UIState<List<ScheduleResponse>>
}