package com.plavsic.skytrace.features.schedule.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.plavsic.skytrace.features.map.model.FlightResponse
import kotlin.math.abs
import kotlin.math.round


@Composable
fun ScheduleView(
    modifier: Modifier = Modifier,
    flight:FlightResponse
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 30.dp)
    ){
        item {
            Schedule(flight = flight)

        }
    }
}


@Composable
fun Schedule(
    modifier: Modifier = Modifier,
    flight:FlightResponse
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxHeight()
    ) {
        ScheduleTitle()
        SchedulePlaneCard()

        ScheduleCard(icon = R.drawable.departure, city="Bucharest",
            airport = "Henri Coanda International Airporttttttttttt",
            direction = "DEPARTURE FRI, 23 AUG", code = "OTP",time = "01:25 PM")

        ScheduleCard(icon = R.drawable.arrival, city="Memmingen",
            airport = "Memmingen Allgau Airport",
            direction = "ARRIVAL FRI, 23 AUG", code = "FMM", time = "02:45 PM")

        GeoTagging(flight = flight)

    }

}


@Composable
fun ScheduleTitle(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 55.dp, vertical = 65.dp)) {
        Text(
            text = "Bucharect - Memmingen",
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

            Text(
                modifier = modifier.padding(start=15.dp),
                text = "Arrives in 34m • 02:45 PM",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun SchedulePlaneCard(modifier: Modifier = Modifier) {
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
                painter = painterResource(id = R.drawable.plane_icon),
                contentDescription = "plane_icon",
                modifier = modifier
                    .size(60.dp)
                    .padding(bottom = 30.dp, end = 16.dp),
                tint = Color.Gray
            )

            Column{
                Text(
                    text = "W43099",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Wizz Air Malta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00AEEF)
                )
                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = "Duration: 2h 20min",
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
    // terminal:String?
    // gate:String?
    // baggage:String?
    modifier: Modifier = Modifier,
    @DrawableRes icon:Int,
    city:String,
    direction:String,
    airport:String,
    code:String,
    time:String

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
                    text = direction,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = city,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = time,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Text(
                    text = airport,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = code,
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
                        text = "--",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "--",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = modifier.padding(horizontal = 65.dp)
                    )
                    Text(
                        text = "--",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = modifier.padding(horizontal = 5.dp)
                    )
                }

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


private fun convertToKmh(speed:Double):Int{
    return round(speed * 1.852).toInt()
}

private fun convertToDMS(coordinate: Double, isLatitude: Boolean): String {
    val direction = if (isLatitude) {
        if (coordinate >= 0) "N" else "S"
    } else {
        if (coordinate >= 0) "E" else "W"
    }

    val absCoordinate = abs(coordinate)
    val degrees = absCoordinate.toInt()
    val minutesFull = (absCoordinate - degrees) * 60
    val minutes = minutesFull.toInt()
    val seconds = (minutesFull - minutes) * 60

    return "%s %d°%02d'%07.4f\"".format(direction, degrees, minutes, seconds)
}