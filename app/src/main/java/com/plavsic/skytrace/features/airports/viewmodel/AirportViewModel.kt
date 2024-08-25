package com.plavsic.skytrace.features.airports.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plavsic.skytrace.features.airports.data.local.entity.AirportEntity
import com.plavsic.skytrace.features.airports.data.local.entity.AirportWithCity
import com.plavsic.skytrace.features.airports.data.local.entity.FlightAirports
import com.plavsic.skytrace.features.airports.repository.AirportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val airportRepository: AirportRepository
): ViewModel(){

    private val _airport:MutableState<FlightAirports?> = mutableStateOf(null)
    val airport: State<FlightAirports?> = _airport

    var isLoading by mutableStateOf(false)


    fun fetchFlightAirports(departureCode:String,arrivalCode:String) {
        isLoading = true
        viewModelScope.launch {
            val response = airportRepository
                .getFlightAirports(departureCode=departureCode,arrivalCode=arrivalCode)
            _airport.value = response
            isLoading = false
        }
    }
}