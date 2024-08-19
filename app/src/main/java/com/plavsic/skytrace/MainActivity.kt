package com.plavsic.skytrace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plavsic.skytrace.features.map.view.MapScreen
import com.plavsic.skytrace.features.map.viewmodel.FlightTrackerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val flightTrackerViewModel: FlightTrackerViewModel = viewModel()
            MapScreen(viewModel = flightTrackerViewModel)
    }
}
}






