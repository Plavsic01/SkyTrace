package com.plavsic.skytrace.features.futureFlight.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plavsic.skytrace.features.futureFlight.model.FutureFlightResponse
import com.plavsic.skytrace.features.futureFlight.repository.FutureFlightRepository
import com.plavsic.skytrace.utils.resource.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FutureFlightViewModel @Inject constructor(
    private val repository: FutureFlightRepository
):ViewModel(){

    private val _state: MutableState<UIState<List<FutureFlightResponse>>> = mutableStateOf((UIState.Idle))
    val futureFlights: State<UIState<List<FutureFlightResponse>>> = _state


     fun fetchFutureFlights(
        iataCode:String,
        type:String,
        date:String,
        airlineIata:String? = null,
        flightNum:String? = null
        ){
        viewModelScope.launch {
            _state.value = UIState.Loading
            val response = repository.getFutureFlights(
                iataCode = iataCode,
                type = type,
                date = date,
                airlineIata = airlineIata,
                flightNum = flightNum
            )
            _state.value = response
        }
    }

}