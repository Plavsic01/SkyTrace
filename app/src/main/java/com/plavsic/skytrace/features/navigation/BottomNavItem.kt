package com.plavsic.skytrace.features.navigation

import androidx.annotation.DrawableRes
import com.plavsic.skytrace.R


sealed class BottomNavItem(val route: String, val title: String, @DrawableRes val selectedIcon: Int,@DrawableRes val unselectedIcon:Int) {
    data object Map : BottomNavItem(route = "map", title = "Map", selectedIcon = R.drawable.selected_map, unselectedIcon = R.drawable.unselected_map)
    data object FutureFlights : BottomNavItem(route = "futureFlights", title = "Future Flights", selectedIcon =R.drawable.selected_future, unselectedIcon = R.drawable.unselected_future)
    data object Airport : BottomNavItem(route = "airport", title = "Airports", selectedIcon =R.drawable.selected_airport, unselectedIcon = R.drawable.unselected_airport)
    data object Aircraft : BottomNavItem(route = "aircraft", title = "Aircraft", selectedIcon =R.drawable.selected_aircraft, unselectedIcon = R.drawable.unselected_aircraft)
}