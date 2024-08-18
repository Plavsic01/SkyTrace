package com.plavsic.skytrace

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.plavsic.skytrace.model.data.FlightResponse
import com.plavsic.skytrace.utils.UIState
import com.plavsic.skytrace.viewmodel.flightracker.FlightTrackerViewModel
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val flightTrackerViewModel:FlightTrackerViewModel = viewModel()
            Test(viewModel = flightTrackerViewModel)
    }
}
}


@Composable
fun Test(
    modifier: Modifier = Modifier,
    viewModel: FlightTrackerViewModel
) {
    val state by viewModel.flights

    Text(text = "LATITUDE: $state")

    when(state){
        is UIState.Loading -> {
            CircularProgressIndicator()
        }
        is UIState.Success -> {
            println((state as UIState.Success<FlightResponse>).data)

        }
        is UIState.Error -> {
            println((state as UIState.Error).message)
        }
    }
}


