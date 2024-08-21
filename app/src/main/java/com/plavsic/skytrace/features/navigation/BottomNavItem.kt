package com.plavsic.skytrace.features.navigation

import androidx.annotation.DrawableRes
import com.plavsic.skytrace.R


sealed class BottomNavItem(val route: String, val title: String, @DrawableRes val selectedIcon: Int,@DrawableRes val unselectedIcon:Int) {
    data object Map : BottomNavItem(route = "map", title = "Map", selectedIcon = R.drawable.selected_map, unselectedIcon = R.drawable.unselected_map)
    data object NearMe : BottomNavItem(route = "nearMe", title = "Near me", selectedIcon =R.drawable.selected_near_me, unselectedIcon = R.drawable.unselected_near_me)
}