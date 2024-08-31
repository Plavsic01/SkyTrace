package com.plavsic.skytrace.features.aircraft.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity
import com.plavsic.skytrace.features.aircraft.repository.AircraftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AircraftViewModel @Inject constructor(
    private val repository: AircraftRepository
) : ViewModel(){

    private val _aircrafts: MutableState<List<AircraftEntity>?> = mutableStateOf(null)
    val aircrafts: State<List<AircraftEntity>?> = _aircrafts

    var isLoading by mutableStateOf(false)

     fun fetchAircrafts(
        onError:() -> Unit
    ){
         isLoading = true
        viewModelScope.launch {
            val response = repository.getAircrafts()

            // no data
            if(response == null){
                onError()
            }
            _aircrafts.value = response
            isLoading = false
        }
    }

}