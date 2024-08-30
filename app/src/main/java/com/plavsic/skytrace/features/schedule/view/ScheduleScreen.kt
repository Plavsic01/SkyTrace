package com.plavsic.skytrace.features.schedule.view

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.plavsic.skytrace.features.airport.viewmodel.AirportViewModel
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import com.plavsic.skytrace.features.schedule.viewmodel.ScheduleViewModel
import com.plavsic.skytrace.utils.resource.UIState


@Composable
fun ScheduleScreen(
    flight:FlightResponse
) {
    val context = LocalContext.current

    val viewModel: ScheduleViewModel = hiltViewModel()
    val airportViewModel:AirportViewModel = hiltViewModel()

    val state by viewModel.schedules
    val isLoadingAirport = airportViewModel.isLoading
    val flightAirports by airportViewModel.airport

    var scheduleData by remember {
        mutableStateOf<List<ScheduleResponse>?>(null)
    }

    LaunchedEffect(flight){
        viewModel.fetchSchedules(
            flightNum = flight.flight.number,
            flightIata = flight.flight.iataNumber,
            onSuccess = {
                scheduleData = it
                airportViewModel.fetchFlightAirports(scheduleData!![0].departure.iataCode,
                    scheduleData!![0].arrival.iataCode)
            }
        )
    }

    if(!scheduleData.isNullOrEmpty() && !isLoadingAirport){
        ScheduleView(flight = flight, schedules = scheduleData,flightAirports = flightAirports)
    }


    when(state){
        is UIState.Idle -> {}
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator()
            }
        }
        is UIState.Success -> {} // Did it in line 48

        is UIState.Error.NetworkError -> {
            Toast.makeText(context,"Network Error", Toast.LENGTH_SHORT).show()
        }
        is UIState.Error.ServerError -> {
            Toast.makeText(context,"Server Error",Toast.LENGTH_SHORT).show()
        }
        is UIState.Error.UnknownError -> {
            ScheduleView(flight = flight, schedules = null,flightAirports=null)
        }
    }
}
