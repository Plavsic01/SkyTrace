package com.plavsic.skytrace.features.schedule.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plavsic.skytrace.R
import com.plavsic.skytrace.features.airport.data.local.entity.FlightAirports
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import com.plavsic.skytrace.utils.conversions.Conversions.calculateFlightDuration
import com.plavsic.skytrace.utils.conversions.Conversions.convertToDMS
import com.plavsic.skytrace.utils.conversions.Conversions.convertToKmh
import com.plavsic.skytrace.utils.conversions.Conversions.formatDate
import com.plavsic.skytrace.utils.conversions.Conversions.formatTime
import kotlin.math.round


@Composable
fun ScheduleView(
    flight:FlightResponse,
    schedules:List<ScheduleResponse>?,
    flightAirports: FlightAirports?
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 30.dp)
    ){
        item {
            Schedule(flight = flight,schedules = schedules, flightAirports = flightAirports)
        }
    }
}


@Composable
fun Schedule(
    modifier: Modifier = Modifier,
    flight:FlightResponse,
    schedules:List<ScheduleResponse>?,
    flightAirports: FlightAirports?
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxHeight()
    ) {
//        println(schedules?.size)

        if(schedules.isNullOrEmpty()){
//            ScheduleTitle()
            SchedulePlaneCard()
            ScheduleCard(icon = R.drawable.departure)
            ScheduleCard(icon = R.drawable.arrival)
        }else{
            ScheduleTitle(
                departureCity = flightAirports?.departure?.city?.nameCity,
                arrivalCity = flightAirports?.arrival?.city?.nameCity
            )
            SchedulePlaneCard(
                flightIataNum = schedules[0].flight.iataNumber,
                airline = schedules[0].airline.name,
                duration = calculateFlightDuration(schedules[0],flightAirports)
            )
            if(schedules.size == 1) {
                ScheduleCard(
                    icon = R.drawable.departure,
                    city = flightAirports?.departure?.city?.nameCity,
                    airport = flightAirports?.departure?.airport?.nameAirport,
                    direction = formatDate(schedules[0].departure.scheduledTime),
                    code = schedules[0].departure.iataCode,
                    time = formatTime(schedules[0].departure.scheduledTime),
                    terminal = schedules[0].departure.terminal,
                    gate = schedules[0].departure.gate,
                    baggage = schedules[0].departure.baggage,
                    gmt = flightAirports?.departure?.airport?.GMT
                )

                ScheduleCard(
                    icon = R.drawable.arrival,
                    city = flightAirports?.arrival?.city?.nameCity,
                    airport = flightAirports?.arrival?.airport?.nameAirport,
                    direction = formatDate(schedules[0].arrival.scheduledTime),
                    code = schedules[0].arrival.iataCode,
                    time = formatTime(schedules[0].arrival.scheduledTime),
                    terminal = schedules[0].arrival.terminal,
                    gate = schedules[0].arrival.gate,
                    baggage = schedules[0].arrival.baggage,
                    gmt = flightAirports?.arrival?.airport?.GMT
                )
            }else{
                    ScheduleCard(
                        icon = R.drawable.departure,
                        city = flightAirports?.departure?.city?.nameCity,
                        airport = flightAirports?.departure?.airport?.nameAirport,
                        direction = formatDate(schedules[0].departure.scheduledTime),
                        code = schedules[0].departure.iataCode,
                        time = formatTime(schedules[0].departure.scheduledTime),
                        terminal = schedules[0].departure.terminal,
                        gate = schedules[0].departure.gate,
                        baggage = schedules[0].departure.baggage,
                        gmt = flightAirports?.departure?.city?.GMT
                    )

                    ScheduleCard(
                        icon = R.drawable.arrival,
                        city = flightAirports?.arrival?.city?.nameCity,
                        airport = flightAirports?.arrival?.airport?.nameAirport,
                        direction = formatDate(schedules[1].arrival.scheduledTime),
                        code = schedules[1].arrival.iataCode,
                        time = formatTime(schedules[1].arrival.scheduledTime),
                        terminal = schedules[1].arrival.terminal,
                        gate = schedules[1].arrival.gate,
                        baggage = schedules[1].arrival.baggage,
                        gmt = flightAirports?.arrival?.city?.GMT
                    )
                }
            }
        }

        GeoTagging(flight = flight)

    }


@Composable
fun ScheduleTitle(
    modifier: Modifier = Modifier,
    departureCity:String? = null,
    arrivalCity:String? = null
) {

    val cities:String = if(!departureCity.isNullOrEmpty() || !arrivalCity.isNullOrEmpty()){
        "$departureCity - $arrivalCity"
    }else{
        "N/A - N/A"
    }

    Column(modifier = modifier.padding(horizontal = 55.dp, vertical = 65.dp)) {
        Text(
            text = cities,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Clip,
            modifier = modifier.padding(bottom = 15.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            EnRouteTag()

//            Text(
//                modifier = modifier.padding(start=15.dp),
//                text = "Arrives in 34m • 02:45 PM",
//                fontSize = 14.sp,
//                color = Color.Gray
//            )
        }
    }
}


@Composable
fun SchedulePlaneCard(
    modifier: Modifier = Modifier,
    flightIataNum:String? = null,
    airline:String? = null,
    duration:String? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                painter = painterResource(id = R.drawable.plane_icon_32x32),
                contentDescription = "plane_icon",
                modifier = modifier
                    .size(60.dp)
                    .padding(bottom = 30.dp, end = 16.dp),
                tint = Color.Gray
            )

            Column{
                Text(
                    text = flightIataNum ?: "N/A",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = airline ?: "N/A",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00AEEF)
                )
                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = duration ?: "N/A",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

            }


        }

    }
}


@Composable
fun ScheduleCard(
    modifier: Modifier = Modifier,
    @DrawableRes icon:Int,
    city:String? = null,
    direction:String? = null,
    airport:String? = null,
    code:String? = null,
    time:String? = null,
    terminal:String? = null,
    gate:String? = null,
    baggage:String? = null,
    gmt:String? = null

) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "plane_icon",
                modifier = modifier
                    .size(70.dp)
                    .padding(bottom = 30.dp, end = 16.dp),
                tint = Color(0xFF00AEEF)
            )

            Column{
                Text(
                    text = direction ?: "N/A",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = city ?: "N/A",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = time ?: "N/A",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Text(
                    text = airport ?: "N/A",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = code ?: "N/A",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00AEEF)
                )


                Spacer(modifier = modifier.height(8.dp))

                Row {
                    Text(
                        text = "Terminal",
                        fontSize = 14.sp,
                        color = Color.Gray,
                    )
                    Text(
                        text = "Baggage",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = modifier.padding(horizontal = 15.dp)
                    )
                    Text(
                        text = "Gate",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = modifier.padding(horizontal = 5.dp)
                    )
                }

                Row {
                    Text(
                        text = terminal ?: "--",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = baggage ?: "--",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = modifier.padding(horizontal = 65.dp)
                    )
                    Text(
                        text = gate ?: "--",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = modifier.padding(horizontal = 5.dp)
                    )
                }

                Spacer(modifier = modifier.height(4.dp))

                Divider(
                    color = Color.Gray,
                    thickness = 0.2.dp
                )

                Text(
                    text = if(!gmt.isNullOrEmpty()){
                        if(gmt.toInt() >= 0){
                            "GMT+$gmt"
                        }else{
                            "GMT-$gmt"
                        }
                    }else "N/A",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = modifier.padding(horizontal = 5.dp, vertical = 4.dp)
                )

            }


        }
    }
}


@Composable
fun GeoTagging(
    modifier: Modifier = Modifier,
    flight: FlightResponse
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            Text(text = "Geo-tagging")

            Spacer(modifier = modifier.height(16.dp))

            GeoTaggingRow(label = "Altitude", value = "${round(flight.geography.altitude).toInt()} meters")
            GeoTaggingRow(label = "Speed", value = "${convertToKmh(flight.speed.horizontal)} km/h")
            GeoTaggingRow(label = "Heading", value = "${flight.geography.direction.toInt()}°")
            GeoTaggingRow(label = "Latitude", value = convertToDMS(flight.geography.latitude,true))
            GeoTaggingRow(label = "Longitude", value = convertToDMS(flight.geography.longitude,false))
        }
    }
}

@Composable
fun GeoTaggingRow(
    modifier:Modifier = Modifier,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(text = value)
    }
}


@Composable
fun EnRouteTag() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = Color(0xFF00C2D1), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "EN ROUTE",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
