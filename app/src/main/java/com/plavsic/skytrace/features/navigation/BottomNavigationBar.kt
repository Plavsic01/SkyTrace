package com.plavsic.skytrace.features.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
//                            saveState = true
                        }
                        launchSingleTop = true
//                        restoreState = true
                    }
                }
            )
        }
    }













//    Scaffold(
//        bottomBar = {
//            NavigationBar {
//                items.forEachIndexed { index, item ->
//                    NavigationBarItem(
//                        selected = selectedItemIndex == index,
//                        onClick = {
//                            selectedItemIndex = index
//                            // navController.navigate(item.title)
//                        },
//                        label = {
//                            Text(text = item.title)
//                        },
//                        icon = {
//                            Icon(
//                                imageVector = if (index == selectedItemIndex) {
//                                    item.selectedIcon
//                                } else item.unselectedIcon,
//                                contentDescription = item.title
//                            )
//                        }
//                    )
//                }
//            }
//        }
//    ) {
//
//    }
}

