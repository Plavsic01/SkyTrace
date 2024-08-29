package com.plavsic.skytrace.features.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items:List<BottomNavItem>
) {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { index,item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                icon = {
                    Image(
                        painter = painterResource(id = if(index == selectedItemIndex) item.selectedIcon else item.unselectedIcon),
                        contentDescription = item.title
                    )

                },
                label = { Text(text = item.title)},
                onClick = {
                    selectedItemIndex = index
                    if(currentRoute!! != item.route){
                        navController.navigate(item.route){
                            popUpTo(currentRoute.toString()) {inclusive = true}
                            launchSingleTop = true
                        }
                    }

                }
            )
        }
    }


}

