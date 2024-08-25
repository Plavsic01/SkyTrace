package com.plavsic.skytrace.features.map.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.plavsic.skytrace.R
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import com.plavsic.skytrace.features.schedule.view.ScheduleScreen
import com.plavsic.skytrace.features.schedule.view.ScheduleView
import com.plavsic.skytrace.features.schedule.viewmodel.ScheduleViewModel
import com.plavsic.skytrace.utils.resource.UIState

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
                .size(25.dp)
                .rotate(flight.geography.direction.toFloat()),
            tint = if(!isClicked.value) Color(0xFF1E90FF)  else Color(0xFFDC143C)
        )

        if(isClicked.value){
            PartialBottomSheet(showBottomSheet = isClicked) {
                ScheduleScreen(flight = flight)
            }
        }
    }
}


@Composable
fun ShowViewAnnotations(
    flights:List<FlightResponse>
) {
    // probati staviiti unutar LaunchedEffect da mozda asinhrono se podaci prikazuju bez da zabode mapbox
    for(flight in flights){
        PlaneViewAnnotation(flight = flight)
    }
}


