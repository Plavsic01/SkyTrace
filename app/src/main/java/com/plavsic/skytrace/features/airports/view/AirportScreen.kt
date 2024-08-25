package com.plavsic.skytrace.features.airports.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.plavsic.skytrace.features.airports.viewmodel.AirportViewModel


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