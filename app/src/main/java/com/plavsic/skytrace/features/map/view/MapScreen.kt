package com.plavsic.skytrace.features.map.view

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
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
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.plavsic.skytrace.R
import com.plavsic.skytrace.features.map.viewmodel.FlightTrackerViewModel
import kotlin.random.Random

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel:FlightTrackerViewModel
) {

    var mapView: MapView? by remember { mutableStateOf(null) }
    var annotationView: View? by remember { mutableStateOf(null) }
    val showBottomSheet = remember { mutableStateOf(false) }
    var currentPoint: Point by remember {
        mutableStateOf(Point.fromLngLat(20.4606,44.7871)) }

    val initialCameraOptions = CameraOptions.Builder()
        .center(currentPoint)
        .zoom(4.0)
        .pitch(35.0)
        .build()


    Box(modifier = modifier.fillMaxSize()){
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {context ->

                val mapInitOptions = MapInitOptions(
                    context = context,
                    cameraOptions = initialCameraOptions
                )

                MapView(context,mapInitOptions).apply {
                    this.mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
                    val viewAnnotationManager = this.viewAnnotationManager
                    annotationView = viewAnnotationManager.addViewAnnotation(
                        resId = R.layout.annotation_view,
                        options = viewAnnotationOptions {
                            geometry(currentPoint)
                        }
                    )
                    mapView = this
                }

            },
            update = {
                annotationView?.setOnClickListener {
                    showBottomSheet.value = true
                }
            }
        )

        Button(
            onClick = {
                val long = Random.nextDouble(0.0,100.0)
                val lat = Random.nextDouble(0.0,100.0)
                currentPoint = Point.fromLngLat(long, lat) // New coordinates
                annotationView?.let {
                    mapView?.apply {
                        val cameraOptions = CameraOptions.Builder()
                            .center(currentPoint)
                            .zoom(3.0)
                            .pitch(35.0)
                            .build()
//                        this.mapboxMap.setCamera(cameraOptions)
                        this.mapboxMap.easeTo(
                            cameraOptions,
                            MapAnimationOptions.mapAnimationOptions { duration(3_000) }
                        )
                    }
                    mapView?.viewAnnotationManager?.updateViewAnnotation(
                        view = it,
                        options = viewAnnotationOptions {
                            geometry(currentPoint)
                        }
                    )
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text("Change Position")
        }



        if(showBottomSheet.value){
            println(currentPoint)
            PartialBottomSheet(showBottomSheet = showBottomSheet) {
                Text(text = "Ovo je avion koji je trenutno na lokaciji Long: " +
                        "${currentPoint.longitude()} i Lat: ${currentPoint.latitude()}")
            }
        }
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
        }
    ) {
        content()
    }
}