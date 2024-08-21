package com.plavsic.skytrace.features.schedule.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import com.plavsic.skytrace.features.schedule.repository.ScheduleRepository
import com.plavsic.skytrace.utils.resource.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
):ViewModel() {


    private val _state: MutableState<UIState<List<ScheduleResponse>>> = mutableStateOf((UIState.Loading))
    val schedules: State<UIState<List<ScheduleResponse>>> = _state


    init {
        fetchSchedules()
    }

    private fun fetchSchedules(){
        viewModelScope.launch {
            _state.value = UIState.Loading
            val response = repository.getSchedules(limit = 1)
            _state.value = response
        }
    }
}