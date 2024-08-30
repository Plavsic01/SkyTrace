package com.plavsic.skytrace.features.map.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraBoundsOptions
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.plavsic.skytrace.R
import com.plavsic.skytrace.features.map.model.FlightResponse


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun MapBox(
    modifier: Modifier = Modifier,
    flights:List<FlightResponse>?,
    mapViewportState: MapViewportState
){

    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val currentLocation = remember { mutableStateOf<Point?>(null) }


    Box(
        modifier = Modifier.fillMaxSize()
    ){
        MapBoxView(
            modifier = modifier,
            mapViewportState = mapViewportState,
            flights = flights
        )

        LocationButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 120.dp, end = 10.dp),
            locationPermissionState = locationPermissionState,
            fusedLocationClient = fusedLocationClient,
            currentLocation = currentLocation,
            mapViewportState = mapViewportState,
            context = context
        )
    }
}

@Composable
fun MapBoxView(
    modifier: Modifier = Modifier,
    mapViewportState:MapViewportState,
    flights:List<FlightResponse>?
) {

    MapboxMap(
        modifier.fillMaxSize(),
        mapViewportState = mapViewportState,
        style = {
            MapStyle(style = Style.OUTDOORS)
        },
    ) {
        MapEffect(Unit) { mapView ->
            val cameraBoundsOptions = CameraBoundsOptions.Builder()
                .minZoom(2.5)
                .maxZoom(7.0)
                .build()
            mapView.mapboxMap.setBounds(cameraBoundsOptions)
        }

        if(!flights.isNullOrEmpty()){
            ShowViewAnnotations(flights = flights)
        }

    }

}


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun LocationButton(
    modifier: Modifier = Modifier,
    locationPermissionState:PermissionState,
    fusedLocationClient:FusedLocationProviderClient,
    currentLocation:MutableState<Point?>,
    mapViewportState: MapViewportState,
    context: Context

) {
    IconButton(
        onClick = {
            if(locationPermissionState.status.isGranted){
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        currentLocation.value = Point.fromLngLat(it.longitude, it.latitude)
                        mapViewportState.flyTo(
                            cameraOptions = cameraOptions {
                                center(currentLocation.value)
                                zoom(6.0)
                            },
                            animationOptions = MapAnimationOptions.mapAnimationOptions { duration(5000) }
                        )

                    }
                }
            }else if(locationPermissionState.status.shouldShowRationale){
                locationPermissionState.launchPermissionRequest()

            }else if(!locationPermissionState.status.isGranted){
                    Toast.makeText(
                        context,
                        "Please enable location permissions in Settings",
                        Toast.LENGTH_LONG
                    ).show()
            }
        },
        modifier = modifier

    ) {
        Image(
            painter = painterResource(id = R.drawable.location),
            contentDescription = "My location"
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    content: @Composable () -> Unit
){
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = {
            showBottomSheet.value = false
        },
        containerColor = Color.White
    ) {
        content()
    }
}
