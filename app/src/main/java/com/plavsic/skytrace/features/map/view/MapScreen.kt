package com.plavsic.skytrace.features.map.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLngBounds
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.rememberMapState
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.map.viewmodel.FlightTrackerViewModel
import com.plavsic.skytrace.features.schedule.viewmodel.ScheduleViewModel
import com.plavsic.skytrace.utils.conversions.Conversions
import com.plavsic.skytrace.utils.resource.UIState
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun MapScreen(
    viewModel:FlightTrackerViewModel
) {
    val state by viewModel.flights

    val mapViewportState = rememberMapViewportState{
        setCameraOptions {
            center(Point.fromLngLat(0.0,0.0))
            zoom(3.0)
        }
    }

    MapUIState(state = state, mapViewportState = mapViewportState)

}


@Composable
fun MapUIState(
    state:UIState<List<FlightResponse>>,
    mapViewportState:MapViewportState
) {

    var previousZoomLevel by remember { mutableDoubleStateOf(10.0) }
    var previousCenter by remember { mutableStateOf(Point.fromLngLat(-74.0, 40.0)) }


    val flights = remember{
        mutableStateOf<List<FlightResponse>?>(null)
    }

    MapBox(
        modifier = Modifier.fillMaxSize(),
        flights = flights.value,
        mapViewportState = mapViewportState
    )

    when(state){
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        }
        is UIState.Success -> {
            val flightsData = state.data

            LaunchedEffect(mapViewportState.cameraState) {
                val newZoomLevel = mapViewportState.cameraState?.zoom
                val newCenter = mapViewportState.cameraState?.center

                // Ovo racunanje mogu staviti u neki util i vratiti samo true ili false

                if (newCenter != null) {
                    if (newZoomLevel != null) {

                            delay(500)
                            previousCenter = newCenter
                            previousZoomLevel = newZoomLevel

                            val bounds = Conversions
                                .getLatLngBoundsFromCameraPosition(mapViewportState.cameraState!!)

                            flights.value = Conversions.filterPlanesByVisibleRegion(flightsData,bounds)
                            println(flights.value)
                            println(flights.value!!.size)

//                        }
                    }
                }

            }


        }
        is UIState.Error.NetworkError -> {}
        is UIState.Error.ServerError -> {}
        is UIState.Error.UnknownError -> {}
    }
}












