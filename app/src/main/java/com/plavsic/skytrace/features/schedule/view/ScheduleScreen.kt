package com.plavsic.skytrace.features.schedule.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import com.plavsic.skytrace.features.schedule.viewmodel.ScheduleViewModel
import com.plavsic.skytrace.utils.resource.UIState


@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel:ScheduleViewModel = viewModel()
) {

    Text(text = "SCHEDULE SCREEN")

    val state by viewModel.schedules

    if(state is UIState.Success){
        println((state as UIState.Success<List<ScheduleResponse>>).data)
    }




}