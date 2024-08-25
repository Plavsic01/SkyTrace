package com.plavsic.skytrace.features.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plavsic.skytrace.features.airports.view.AirportScreen
import com.plavsic.skytrace.features.airports.viewmodel.AirportViewModel
import com.plavsic.skytrace.features.map.view.MapScreen
import com.plavsic.skytrace.features.map.viewmodel.FlightTrackerViewModel


@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Map.route
    ){
        composable(route = BottomNavItem.Map.route){
            val flightTrackerViewModel: FlightTrackerViewModel = hiltViewModel()
            MapScreen(viewModel = flightTrackerViewModel)
        }

        composable(route = BottomNavItem.NearMe.route){
            val airportViewModel:AirportViewModel = hiltViewModel()
            AirportScreen(viewModel = airportViewModel)
        }
    }
}