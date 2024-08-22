package com.plavsic.skytrace.features.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plavsic.skytrace.features.location.utils.LocationUtils
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
            val context = LocalContext.current
            val locationUtils = LocationUtils(context)
            MapScreen(viewModel = flightTrackerViewModel) //,context = context, locationUtils = locationUtils
        }

        composable(route = BottomNavItem.NearMe.route){
            val scheduleViewModel: ScheduleViewModel = hiltViewModel()
            ScheduleScreen(viewModel = scheduleViewModel)
        }
    }
}