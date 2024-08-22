package com.plavsic.skytrace.features.map.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.map.viewmodel.FlightTrackerViewModel
import com.plavsic.skytrace.utils.resource.UIState

@Composable
fun MapScreen(
    viewModel:FlightTrackerViewModel
) {

    val state by viewModel.flights

    val flights = remember{
        mutableStateOf<List<FlightResponse>?>(null)
    }

    MapBox(
        modifier = Modifier.fillMaxSize(),
        flights = flights.value
    )

    LaunchedEffect(Unit) {
        viewModel.fetchFlights()
    }


    when(state){
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        }
        is UIState.Success -> {
            val flightsData = (state as UIState.Success<List<FlightResponse>>).data
                flights.value = flightsData
        }
        is UIState.Error.NetworkError -> {}
        is UIState.Error.ServerError -> {}
        is UIState.Error.UnknownError -> {}
    }


}
















