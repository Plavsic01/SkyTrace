package com.plavsic.skytrace.features.schedule.view

import android.widget.Toast
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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


    // probati promeniti ovo da ne bude onako da se prikaze na milisekund prosli podaci pa tek onda novi
    // mozda resiti da nemam ovaj LaunchedEffect vec samo da pozivam direktno viewModel.fetchSchedules()

    LaunchedEffect(flight){
        viewModel.fetchSchedules(flightNum = flight.flight.number, flightIata = flight.flight.iataNumber)
    }

    when(state){
        is UIState.Idle -> {}
        is UIState.Loading -> {}
        is UIState.Success -> {
            val schedules = (state as UIState.Success<List<ScheduleResponse>>).data
            LaunchedEffect(Unit) {
                airportViewModel.fetchFlightAirports(schedules[0].departure.iataCode,schedules[0].arrival.iataCode)
            }
            if(!isLoadingAirport){
                ScheduleView(flight = flight, schedules = schedules,flightAirports = flightAirports)
            }else{
                CircularProgressIndicator()
            }
        }

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
