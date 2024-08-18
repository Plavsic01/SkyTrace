package com.plavsic.skytrace.viewmodel.flightracker

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plavsic.skytrace.model.data.FlightResponse
import com.plavsic.skytrace.repository.FlightTrackerRepository
import com.plavsic.skytrace.utils.UIState
import kotlinx.coroutines.launch

class FlightTrackerViewModel(
    private val flightTrackerRepository: FlightTrackerRepository = FlightTrackerRepository()
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
                val response = flightTrackerRepository.getFlights()
                _state.value = UIState.Success(response)
            }catch (e:Exception){
                _state.value = UIState.Error(e.message!!)
            }
        }
    }


}