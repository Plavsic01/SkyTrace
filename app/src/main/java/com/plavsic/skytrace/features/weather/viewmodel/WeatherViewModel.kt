package com.plavsic.skytrace.features.weather.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plavsic.skytrace.features.weather.model.WeatherResponse
import com.plavsic.skytrace.features.weather.repository.WeatherRepository
import com.plavsic.skytrace.utils.resource.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository:WeatherRepository
):ViewModel() {

    private val _weather: MutableState<UIState<WeatherResponse>> = mutableStateOf(UIState.Idle)
    val weather: State<UIState<WeatherResponse>> = _weather


    fun fetchWeatherData(lat:Double,lon:Double){
        _weather.value = UIState.Loading
        viewModelScope.launch {
            val response = repository.getWeather(lat,lon, units = "metric")
            _weather.value = response
        }
    }

}