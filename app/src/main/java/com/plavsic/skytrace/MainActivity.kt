package com.plavsic.skytrace

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.plavsic.skytrace.features.navigation.BottomNavGraph
import com.plavsic.skytrace.features.navigation.BottomNavItem
import com.plavsic.skytrace.features.navigation.BottomNavigationBar
import com.plavsic.skytrace.ui.theme.SkyTraceTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkyTraceTheme(
                darkTheme = false
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val navItems = listOf(
        BottomNavItem.Map,
        BottomNavItem.NearMe
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = navItems)
        }
    ) {
        BottomNavGraph(navController = navController)
    }
}
