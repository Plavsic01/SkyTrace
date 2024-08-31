package com.plavsic.skytrace.features.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plavsic.skytrace.features.aircraft.view.AircraftScreen
import com.plavsic.skytrace.features.airport.view.AirportScreen
import com.plavsic.skytrace.features.airport.viewmodel.AirportViewModel
import com.plavsic.skytrace.features.futureFlight.view.FutureFlightsScreen
import com.plavsic.skytrace.features.futureFlight.viewmodel.FutureFlightViewModel
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
        composable(
            route = BottomNavItem.Map.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            },
            exitTransition = {
                return@composable fadeOut(tween(10))
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,tween(700)
                )
            },
            popExitTransition = {
                return@composable fadeOut(tween(100))
            }
        ){
            MapScreen()
        }

        composable(
            route = BottomNavItem.FutureFlights.route,
        ){

            FutureFlightsScreen()
        }

        composable(
            route = BottomNavItem.Airport.route,
        ){
            AirportScreen()
        }

        composable(
            route = BottomNavItem.Aircraft.route,
        ){
            AircraftScreen()
        }
    }
}