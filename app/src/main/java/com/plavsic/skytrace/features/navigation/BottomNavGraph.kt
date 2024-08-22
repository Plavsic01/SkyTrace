package com.plavsic.skytrace.features.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plavsic.skytrace.features.map.view.MapScreen
import com.plavsic.skytrace.features.map.viewmodel.FlightTrackerViewModel
import com.plavsic.skytrace.features.schedule.view.ScheduleScreen
import com.plavsic.skytrace.features.schedule.viewmodel.ScheduleViewModel


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
            val scheduleViewModel: ScheduleViewModel = hiltViewModel()
            ScheduleScreen(viewModel = scheduleViewModel)
        }
    }
}