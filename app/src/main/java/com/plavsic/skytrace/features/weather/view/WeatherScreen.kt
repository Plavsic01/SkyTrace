package com.plavsic.skytrace.features.weather.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.plavsic.skytrace.features.aircraft.view.AircraftScreen
import com.plavsic.skytrace.features.airport.data.local.entity.AirportWithCity
import com.plavsic.skytrace.features.weather.model.SystemInfo
import com.plavsic.skytrace.features.weather.model.Weather
import com.plavsic.skytrace.features.weather.model.WeatherInfo
import com.plavsic.skytrace.features.weather.model.WeatherResponse
import com.plavsic.skytrace.features.weather.model.Wind
import com.plavsic.skytrace.features.weather.viewmodel.WeatherViewModel
import com.plavsic.skytrace.utils.conversions.Conversions.convertUnixToLocalTimeWithOffset
import com.plavsic.skytrace.utils.conversions.Conversions.formatTime
import com.plavsic.skytrace.utils.resource.UIState

@Composable
fun WeatherScreen(
    airportAndCity: AirportWithCity
) {
    val context = LocalContext.current
    val viewModel:WeatherViewModel = hiltViewModel()

    val weather = viewModel.weather

    LaunchedEffect(Unit) {
        viewModel.fetchWeatherData(airportAndCity.city.latitudeCity,airportAndCity.city.longitudeCity)
    }

    when(weather.value){
        is UIState.Idle -> {}
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator()
            }
        }
        is UIState.Success -> {
            WeatherBottomSheet(weather = (weather.value as UIState.Success<WeatherResponse>).data)
        }
        is UIState.Error.NetworkError -> {
            Toast.makeText(context,"Network Error", Toast.LENGTH_SHORT).show()
        }
        is UIState.Error.ServerError -> {
            val error = (weather.value as UIState.Error.ServerError)
            Toast.makeText(context,"Server Error ${error.code} - ${error.message}", Toast.LENGTH_SHORT).show()
        }
        is UIState.Error.UnknownError -> {
            Toast.makeText(context,"Unknown Error", Toast.LENGTH_SHORT).show()
        }
    }
}


@Composable
fun WeatherBottomSheet(weather: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 60.dp)
    ) {
        TemperatureCard(main = weather.main, weather = weather.weather[0])
        HumidityPressureCard(main = weather.main)
        WindCard(wind = weather.wind, visibility = weather.visibility)
        SunriseSunsetCard(sys = weather.sys,timezone = weather.timezone)
    }
}



@Composable
fun TemperatureCard(
    main: WeatherInfo,
    weather:Weather
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F9FA)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Temperature",
                    color = Color(0xFF343A40),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Image(
                    painter = rememberAsyncImagePainter(model = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"),
                    contentDescription = weather.description,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Current: ${main.temp}°C",
                color = Color(0xFF6C757D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "Feels like: ${main.feels_like}°C",
                color = Color(0xFF6C757D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "Min: ${main.temp_min}°C, Max: ${main.temp_max}°C",
                color = Color(0xFF6C757D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun HumidityPressureCard(main: WeatherInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F9FA)
        )

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Humidity & Pressure",
                color = Color(0xFF343A40),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
                )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Humidity: ${main.humidity}%",
                color = Color(0xFF6C757D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "Pressure: ${main.pressure} hPa",
                color = Color(0xFF6C757D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun WindCard(wind: Wind, visibility: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F9FA)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Wind & Visibility",
                color = Color(0xFF343A40),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
                )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Speed: ${wind.speed} m/s",
                color = Color(0xFF6C757D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "Direction: ${wind.deg}°",
                color = Color(0xFF6C757D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "Visibility: ${visibility / 1000} km",
                color = Color(0xFF6C757D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun SunriseSunsetCard(
    sys: SystemInfo,
    timezone:Int
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F9FA)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sunrise & Sunset",
                color = Color(0xFF343A40),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Image(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = "Sunrise",
                    )
                    Text(
                        text = "Sunrise: ${convertUnixToLocalTimeWithOffset(sys.sunrise,timezone)}",
                        color = Color(0xFF6C757D),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Image(
                        imageVector = Icons.Default.NightsStay,
                        contentDescription = "Sunset"
                    )
                    Text(
                        text = "Sunset: ${convertUnixToLocalTimeWithOffset(sys.sunset,timezone)}",
                        color = Color(0xFF6C757D),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}





