package com.plavsic.skytrace

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

            ExitConfirmationScreen()

        }

    }
}

@Composable
fun ExitConfirmationScreen() {
    var backPressedCount by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val handler = remember { Handler(Looper.getMainLooper()) }

    BackHandler {
        backPressedCount++
        if (backPressedCount == 1) {
            Toast.makeText(context, "Tap back again to exit", Toast.LENGTH_SHORT).show()
            handler.postDelayed({
                backPressedCount = 0
            }, 2000) // Reset after 2 seconds
        } else if (backPressedCount == 2) {
            // Exit the app
            (context as? Activity)?.finish()
        }
    }
}




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val navItems = listOf(
        BottomNavItem.Map,
        BottomNavItem.FutureFlights
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = navItems)
        }
    ) {
        BottomNavGraph(navController = navController)
    }
}
