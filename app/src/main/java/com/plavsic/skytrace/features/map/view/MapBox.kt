package com.plavsic.skytrace.features.map.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.plavsic.skytrace.R
import com.plavsic.skytrace.features.location.model.Location
import com.plavsic.skytrace.features.map.model.FlightResponse

@Composable
fun MapBox(
    modifier: Modifier = Modifier,
    flights:List<FlightResponse>?,
    location: Location
){

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        MapboxMap(
            modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
                setCameraOptions {
                    center(Point.fromLngLat(location.longitude,location.latitude))
                    zoom(2.0)
                }
            },
            style = {
                MapStyle(style = Style.MAPBOX_STREETS)
            }
        ){
            if(!flights.isNullOrEmpty()){
                ShowViewAnnotations(flights = flights)
            }
        }


        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp)
        ) {
            Text("Get Current Location")
        }


    }


}




@Composable
fun PlaneViewAnnotation(
    modifier: Modifier = Modifier,
    flight: FlightResponse
) {

    val isClicked = remember { mutableStateOf(false) }


    ViewAnnotation(
        options = viewAnnotationOptions {
            this.allowOverlap(false)
            this.geometry(Point.fromLngLat(flight.geography.longitude,flight.geography.latitude))
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.plane_icon),
            contentDescription = "plane",
            modifier = modifier
                .size(40.dp)
                .rotate(flight.geography.direction.toFloat())
                .clickable {
                    isClicked.value = !isClicked.value
                }
        )

        if(isClicked.value){
            val currentPoint = Point.fromLngLat(flight.geography.longitude,flight.geography.latitude)
            PartialBottomSheet(showBottomSheet = isClicked) {
                Text(text = "Ovo je avion koji je trenutno na lokaciji Long: " +
                        "${currentPoint?.longitude()} i Lat: ${currentPoint?.latitude()}")
            }
        }
    }
}


@Composable
fun ShowViewAnnotations(
    flights:List<FlightResponse>
) {
    for(flight in flights){
        PlaneViewAnnotation(flight = flight)
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
