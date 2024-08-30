package com.plavsic.skytrace.features.airport.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.plavsic.skytrace.R
import com.plavsic.skytrace.features.airport.data.local.entity.AirportWithCity
import com.plavsic.skytrace.features.airport.viewmodel.AirportViewModel
import com.plavsic.skytrace.features.map.view.PartialBottomSheet
import com.plavsic.skytrace.features.weather.view.WeatherScreen


@Composable
fun AirportScreen(
    viewModel:AirportViewModel
) {
    val context = LocalContext.current

    val airport = viewModel.airportWithCity
    var icaoCode by remember { mutableStateOf("") }
    var cameraPosition by remember { mutableStateOf(Point.fromLngLat(0.0,0.0)) }
    val isLoadingAirport = viewModel.isLoading

    Column {

//        WeatherScreen()

        SearchBar{iataCode ->
            icaoCode = iataCode

            viewModel.fetchAirport(icaoCode){
                Toast.makeText(context,"NO DATA TO SHOW",Toast.LENGTH_SHORT).show()
            }
        }

        if(isLoadingAirport){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }

        if(!isLoadingAirport && airport.value != null){
            cameraPosition = Point.fromLngLat(
                airport.value?.airport?.longitudeAirport ?: 0.0,
                airport.value?.airport?.latitudeAirport ?: 0.0
            )
            MapBoxView(cameraPosition = cameraPosition)
            AirportWithCityCard(airport.value!!)
        }
    }

}


@Composable
fun MapBoxView(
    modifier: Modifier = Modifier,
    cameraPosition:Point
) {
    MapboxMap(
        modifier.height(300.dp),
        mapViewportState = rememberMapViewportState {
            setCameraOptions {
                center(cameraPosition)
                zoom(8.0)
            }
        }

    ) {
        ViewAnnotation(
            modifier = modifier
                .background(Color.Transparent)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {},
            options = viewAnnotationOptions {
                this.allowOverlap(true)
                this.geometry(cameraPosition)
            }
        ) {

            Icon(
                painter = painterResource(id = R.drawable.selected_airport),
                contentDescription = "airport",
                modifier = modifier
                    .size(34.dp),
                tint = Color.Unspecified
            )
        }
    }
}


@Composable
fun SearchBar(
    onSearch: (String) -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = {
                if(it.length <= 3){
                    searchQuery.value = it.uppercase()
                }
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            label = { Text(text = "Search") },
            placeholder = { Text(text = "Airport Iata Code") },
            singleLine = true
        )

        IconButton(onClick = { onSearch(searchQuery.value) }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun AirportWithCityCard(airportAndCity: AirportWithCity) {

    val isClicked = remember { mutableStateOf(false) }

    val airportCountryIataCode = if(airportAndCity.airport.nameCountry.isNotEmpty()){
        "${airportAndCity.airport.nameCountry}, ${airportAndCity.airport.codeIataAirport}"
    }else{
        airportAndCity.airport.codeIataAirport
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEDF2F4)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Airport Information
            Text(
                text = airportAndCity.airport.nameAirport,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D1B2A)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = airportCountryIataCode,
                fontSize = 18.sp,
                color = Color(0xFF1B263B)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Phone: ${if(airportAndCity.airport.phone.isNullOrEmpty()) "N/A" else airportAndCity.airport.phone}",
                fontSize = 14.sp,
                color = Color(0xFF415A77)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = MaterialTheme.colorScheme.secondary, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // City Information
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = airportAndCity.city.nameCity,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B2A)
                )

                Button(onClick = {
                    isClicked.value = true
                }) {
                    Text(
                        text = "Weather",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                if(isClicked.value){
                    PartialBottomSheet(showBottomSheet = isClicked) {
                        WeatherScreen(airportAndCity = airportAndCity)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "City Code: ${airportAndCity.city.codeIataCity}",
                fontSize = 16.sp,
                color = Color(0xFF1B263B)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Coordinates: ${airportAndCity.city.latitudeCity}, ${airportAndCity.city.longitudeCity}",
                fontSize = 14.sp,
                color = Color(0xFF415A77)
            )
        }
    }
}

