package com.plavsic.skytrace.features.map.view

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.map.viewmodel.FlightTrackerViewModel
import com.plavsic.skytrace.utils.conversions.Conversions
import com.plavsic.skytrace.utils.resource.UIState
import kotlinx.coroutines.delay

@Composable
fun MapScreen() {

    val flightTrackerViewModel: FlightTrackerViewModel = hiltViewModel()

    val context = LocalContext.current

    val state by flightTrackerViewModel.flights

    val mapViewportState = rememberMapViewportState{
        setCameraOptions {
            center(Point.fromLngLat(0.0,0.0))
            zoom(2.5)
        }
    }

    val flights = remember{
        mutableStateOf<List<FlightResponse>?>(null)
    }

    MapBox(
        modifier = Modifier.fillMaxSize(),
        flights = flights.value,
        mapViewportState = mapViewportState
    )

    when(state){
        is UIState.Idle -> {}
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        }
        is UIState.Success -> {
            val flightsData = (state as UIState.Success<List<FlightResponse>>).data

            if(mapViewportState.cameraState?.zoom!! > 4.2){
                LaunchedEffect(mapViewportState.cameraState) {
                    val bounds = Conversions
                        .getLatLngBoundsFromCameraPosition(mapViewportState.cameraState!!)

                    delay(100)

                    flights.value = Conversions.filterPlanesByVisibleRegion(flightsData,bounds)
                }

            }
        }
        is UIState.Error.NetworkError -> {
            Toast.makeText(context,"Network Error", Toast.LENGTH_SHORT).show()
        }
        is UIState.Error.ServerError -> {
            Toast.makeText(context,"Server Error",Toast.LENGTH_SHORT).show()
        }
        is UIState.Error.UnknownError -> {
            Toast.makeText(context,"Unknown Error",Toast.LENGTH_SHORT).show()
        }
    }
}












