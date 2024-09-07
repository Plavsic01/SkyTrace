package com.plavsic.skytrace.features.aircraft.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity
import com.plavsic.skytrace.features.aircraft.repository.AircraftRepository
import com.plavsic.skytrace.utils.resource.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AircraftViewModel @Inject constructor(
    private val repository: AircraftRepository
) : ViewModel(){

    private val _aircraft: MutableState<UIState<List<AircraftEntity>>> = mutableStateOf(UIState.Idle)
    val aircraft: State<UIState<List<AircraftEntity>>> = _aircraft

     fun fetchAircraft(){
         _aircraft.value = UIState.Loading
        viewModelScope.launch {
            val response = repository.getAircraft()
            _aircraft.value = response
        }
    }

}