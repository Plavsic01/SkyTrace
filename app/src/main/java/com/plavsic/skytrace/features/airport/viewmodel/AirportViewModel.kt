package com.plavsic.skytrace.features.airport.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plavsic.skytrace.features.airport.data.local.entity.AirportWithCity
import com.plavsic.skytrace.features.airport.data.local.entity.FlightAirports
import com.plavsic.skytrace.features.airport.repository.AirportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val repository: AirportRepository
): ViewModel(){

    private val _airport:MutableState<FlightAirports?> = mutableStateOf(null)
    val airport: State<FlightAirports?> = _airport

    private val _airportWithCity:MutableState<AirportWithCity?> = mutableStateOf(null)
    val airportWithCity:State<AirportWithCity?> = _airportWithCity

    var isLoading by mutableStateOf(false)


    fun fetchFlightAirports(departureCode:String,arrivalCode:String) {
        viewModelScope.launch {
            isLoading = true
            val response = repository
                .getFlightAirports(departureCode=departureCode,arrivalCode=arrivalCode)
            _airport.value = response
            isLoading = false
        }
    }


    fun fetchAirport(
        codeIataAirport:String,
        onError:() -> Unit
    ){
        isLoading = true
        viewModelScope.launch {
            val response = repository
                .getAirportWithCity(codeIataAirport)

            // no data
            if(response == null){
                onError()
            }
            _airportWithCity.value = response
            isLoading = false
        }
    }
}



