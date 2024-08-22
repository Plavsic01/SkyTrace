package com.plavsic.skytrace.features.map.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import com.plavsic.skytrace.features.map.repository.FlightTrackerRepository
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.utils.resource.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightTrackerViewModel @Inject constructor(
    private val repository: FlightTrackerRepository
):ViewModel() {


    private val _state:MutableState<UIState<List<FlightResponse>>> = mutableStateOf((UIState.Loading))
    val flights: State<UIState<List<FlightResponse>>> = _state


//    init {
//        fetchFlights()
//    }

    fun fetchFlights(center:Point,zoomLevel:Double){
        val radius = calculateRadiusBasedOnZoom(zoomLevel)

        val lat = center.latitude()
        val lng = center.longitude()
        val distance = radius

        viewModelScope.launch {
            _state.value = UIState.Loading
            val response = repository
                .getFlights(lat,lng,distance,limit = 100)
            _state.value = response
            println("ODGOVOR: $response")
        }
    }

    private fun calculateRadiusBasedOnZoom(zoomLevel: Double): Int {
        return when {
            zoomLevel > 15 -> 10  // Mali radijus za visoko zumiranje
            zoomLevel > 10 -> 50  // Srednji radijus za srednje zumiranje
            zoomLevel > 5 -> 70
            else -> 10000           // Veliki radijus za malo zumiranje
        }
    }


}