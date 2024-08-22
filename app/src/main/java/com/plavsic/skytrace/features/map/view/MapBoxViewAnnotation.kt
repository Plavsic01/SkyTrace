package com.plavsic.skytrace.features.map.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.plavsic.skytrace.R
import com.plavsic.skytrace.features.map.model.FlightResponse

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