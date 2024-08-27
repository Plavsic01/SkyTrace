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


    init {
        fetchFlights()
    }


    private fun fetchFlights(){

        viewModelScope.launch {
            while(true){
            _state.value = UIState.Loading
            val response = repository
                .getFlights("en-route",limit=5000)
            _state.value = response
            delay(5*60*1000) // Calls API every 5 minutes
            }
        }
    }



    //    fun fetchFlights(center:Point,zoomLevel:Double){
//        val radius = calculateRadiusBasedOnZoom(zoomLevel)
////        val limit = calculateLimitBasedOnZoom(zoomLevel)
//
//        val lat = center.latitude()
//        val lng = center.longitude()
//        val distance = radius
//
//        viewModelScope.launch {
//            _state.value = UIState.Loading
//            val response = repository
//                .getFlights(lat,lng,distance,"en-route")
//            _state.value = response
//        }
//    }

//    private fun calculateRadiusBasedOnZoom(zoomLevel: Double): Int {
//        return when {
//            zoomLevel > 15.0 -> 10  // Mali radijus za visoko zumiranje
//            zoomLevel > 10.0 -> 100  // Srednji radijus za srednje zumiranje
//            zoomLevel > 5.0 -> 200
//            zoomLevel > 3.0 -> 400
//            zoomLevel > 1.0 -> 700
//            else -> 200          // Veliki radijus za malo zumiranje
//        }
//    }



}