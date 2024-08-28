package com.plavsic.skytrace.features.airport.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plavsic.skytrace.features.airport.viewmodel.AirportViewModel


@Composable
fun AirportScreen(
    modifier: Modifier = Modifier,
    viewModel:AirportViewModel
) {
//    val airport = viewModel.airport
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchFlightAirports("AER","SAW")
//    }

//    Column {
//        Text(text = "Airport: ${airport.value?.departure?.airport?.nameAirport}")
//        Text(text = "Airport: ${airport.value?.arrival?.airport?.nameAirport}")
//    }

}