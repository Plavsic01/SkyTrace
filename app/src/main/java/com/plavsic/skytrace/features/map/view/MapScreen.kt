package com.plavsic.skytrace.features.map.view

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plavsic.skytrace.MainActivity
import com.plavsic.skytrace.features.location.model.Location
import com.plavsic.skytrace.features.location.utils.LocationUtils
import com.plavsic.skytrace.features.location.viewmodel.LocationViewModel
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.map.viewmodel.FlightTrackerViewModel
import com.plavsic.skytrace.utils.resource.UIState

@Composable
fun MapScreen(
    viewModel:FlightTrackerViewModel,
    locationUtils: LocationUtils,
    context: Context,
    locationViewModel: LocationViewModel = viewModel()
) {

    val location = locationViewModel.location.value

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) {
                permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true){
                // I HAVE ACCESS TO LOCATION
                locationUtils.requestLocationUpdates(viewModel=locationViewModel)
            }else{
                // ASK FOR PERMISSION
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if(rationaleRequired){
                    Toast.makeText(context,
                        "Location Permission is required for this feature to work",
                        Toast.LENGTH_LONG)
                        .show()
                }else{
                    Toast.makeText(context,
                        "Location Permission is required. Enable it in the Android Settings",
                        Toast.LENGTH_LONG)
                        .show()
                }

            }
        }


        if(locationUtils.hasLocationPermission(context)){
            locationUtils.requestLocationUpdates(viewModel=locationViewModel)
        }else{
            // Request location permission
            requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }

    val state by viewModel.flights


    when(state){
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
//                MapBox(
//                    modifier = Modifier.matchParentSize(),
//                    flights = null
//                )

                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        }
        is UIState.Success -> {
            val flights = (state as UIState.Success<List<FlightResponse>>).data

            MapBox(
                flights = flights,
                location = location ?: Location(0.0,0.0)
            )
        }
        is UIState.Error.NetworkError -> {}
        is UIState.Error.ServerError -> {}
        is UIState.Error.UnknownError -> {}
    }


}
















