package com.plavsic.skytrace.features.map.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
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
        modifier = modifier
            .background(Color.Transparent)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isClicked.value = !isClicked.value
            },
        options = viewAnnotationOptions {
            this.allowOverlap(false)
            this.geometry(Point.fromLngLat(flight.geography.longitude,flight.geography.latitude))
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.plane_icon),
            contentDescription = "plane",
            modifier = modifier
                .size(30.dp)
                .rotate(flight.geography.direction.toFloat()),
            tint = if(!isClicked.value) Color(0xFF1E90FF)  else Color(0xFFDC143C)
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


