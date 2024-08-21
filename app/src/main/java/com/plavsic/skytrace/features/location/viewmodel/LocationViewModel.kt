package com.plavsic.skytrace.features.location.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.plavsic.skytrace.features.location.model.Location

class LocationViewModel:ViewModel() {

    private val _location = mutableStateOf<Location?>(null)
    val location: State<Location?> = _location

    fun updateLocation(newLocation:Location){
        _location.value = newLocation
    }
}