package com.plavsic.skytrace.features.schedule.view

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.plavsic.skytrace.features.airports.viewmodel.AirportViewModel
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import com.plavsic.skytrace.features.schedule.viewmodel.ScheduleViewModel
import com.plavsic.skytrace.utils.resource.UIState
import kotlinx.coroutines.delay


@Composable
fun ScheduleScreen(
    flight:FlightResponse
) {
    val viewModel: ScheduleViewModel = hiltViewModel()
    val airportViewModel:AirportViewModel = hiltViewModel()

    val state by viewModel.schedules
    val isLoadingSchedule = viewModel.isLoading
    val isLoadingAirport = airportViewModel.isLoading

    val flightAirports by airportViewModel.airport


    LaunchedEffect(flight){
        viewModel.fetchSchedules(flightNum = flight.flight.number, flightIata = flight.flight.iataNumber)
    }

//    if(state is UIState.Loading){
//        CircularProgressIndicator()
//    }

    if(state is UIState.Success){
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


//    if(state is UIState.Success){
//        val schedules = (state as UIState.Success<List<ScheduleResponse>>).data
//        LaunchedEffect(Unit) {
//            airportViewModel.fetchFlightAirports(schedules[0].departure.iataCode,schedules[0].arrival.iataCode)
//        }
//        if(!isLoadingAirport){
//            ScheduleView(flight = flight, schedules = schedules,flightAirports = flightAirports)
//        }
//
//    }





    if(state is UIState.Error.UnknownError){
        ScheduleView(flight = flight, schedules = null,flightAirports=null)
    }



}
