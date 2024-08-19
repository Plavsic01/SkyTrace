package com.plavsic.skytrace.features.map.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.map.repository.FlightTrackerRepositoryImpl
import com.plavsic.skytrace.utils.UIState
import kotlinx.coroutines.launch

class FlightTrackerViewModel(
    private val flightTrackerRepository:FlightTrackerRepositoryImpl = FlightTrackerRepositoryImpl()
):ViewModel() {

    private val _state:MutableState<UIState<FlightResponse>> = mutableStateOf((UIState.Loading))
    val flights: State<UIState<FlightResponse>> = _state


    init {
        fetchFlights()
    }

    private fun fetchFlights(){
        viewModelScope.launch {
            try{
                _state.value = UIState.Loading
                val response = flightTrackerRepository.getFlights(limit = 1)
                _state.value = UIState.Success(response)
            }catch (e:Exception){
                _state.value = UIState.Error(e.message!!)
            }
        }
    }


}