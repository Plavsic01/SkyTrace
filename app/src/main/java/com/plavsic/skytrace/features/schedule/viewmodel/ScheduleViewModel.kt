package com.plavsic.skytrace.features.schedule.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import com.plavsic.skytrace.features.schedule.repository.ScheduleRepository
import com.plavsic.skytrace.utils.resource.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
):ViewModel() {

    private val _state: MutableState<UIState<List<ScheduleResponse>>> = mutableStateOf((UIState.Loading))
    val schedules: State<UIState<List<ScheduleResponse>>> = _state

    var isLoading by mutableStateOf(false)
        private set

    fun fetchSchedules(flightNum:String,flightIata:String){
        _state.value = UIState.Loading
        isLoading = true
        viewModelScope.launch {
            val response = repository.getSchedules(flightNum,flightIata,status="active")
            _state.value = response
            isLoading = false
        }
    }
}