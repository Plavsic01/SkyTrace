package com.plavsic.skytrace.utils.conversions

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.mapbox.maps.CameraState
import com.plavsic.skytrace.features.airport.data.local.entity.FlightAirports
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.features.schedule.model.ScheduleResponse
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

object Conversions {

     fun convertToKmh(speed:Double):Int{
        return round(speed * 1.852).toInt()
    }


     fun convertToDMS(coordinate: Double, isLatitude: Boolean): String {
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


    fun formatDate(inputDate: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
        val date: Date? = inputDate?.let { inputFormat.parse(it) }

        val outputFormat = SimpleDateFormat("EEE, dd MMM", Locale.ENGLISH)
        return date?.let { outputFormat.format(it) }
    }

    fun extractDate(dateTime: String): String {
        return if (dateTime.contains("T")) {
            dateTime.substringBefore("T")
        }else{
            "N/A"
        }
    }

    fun convertUnixToLocalTimeWithOffset(unixTime: Long, offsetInSeconds: Int): String {
        val instant = Instant.ofEpochSecond(unixTime)
        val zoneOffset = ZoneOffset.ofTotalSeconds(offsetInSeconds)
        val localDateTime = LocalDateTime.ofInstant(instant, zoneOffset)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return localDateTime.format(formatter)
    }

    fun formatTime(inputDate: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
        val date: Date? = inputDate?.let { inputFormat.parse(it) }

        // Format to AM/PM
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        return date?.let { outputFormat.format(it) }
    }
    
    fun formatDateFromMilliseconds(milliseconds:Long):String{
        val instant = Instant.ofEpochMilli(milliseconds)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
        val dateTimeFormatted = dateTime.format(formatter)

        return  dateTimeFormatted.split(" ")[0].replace("/","-")
    }


    fun calculateFlightDuration(
        schedule: ScheduleResponse,
        flightAirports: FlightAirports?
    ): String {

        val departureDateTimeString = schedule.departure?.scheduledTime.toString()
        val arrivalDateTimeString = schedule?.arrival?.scheduledTime.toString()

        val departureZoneId = flightAirports?.departure?.airport?.GMT
        val arrivalZoneId = flightAirports?.arrival?.airport?.GMT

        if(departureZoneId != null && arrivalZoneId != null){
            val roundedDepartureZoneId = departureZoneId.toDouble().roundToInt()
            val roundedArrivalZoneId = arrivalZoneId.toDouble().roundToInt()

            val departureZone = if(departureZoneId.toDouble() >= 0) "GMT+${roundedDepartureZoneId}" else "GMT${roundedDepartureZoneId}"
            val arrivalZone = if(arrivalZoneId.toDouble() >= 0) "GMT+${roundedArrivalZoneId}" else "GMT${roundedArrivalZoneId}"

            // Formatter for parsing date and time from string
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

            // Parsing string to LocalDateTime objects
            val departureLocalDateTime = LocalDateTime.parse(departureDateTimeString, formatter)
            val arrivalLocalDateTime = LocalDateTime.parse(arrivalDateTimeString, formatter)

            // Creating ZonedDateTime objects with corresponding timezones
            val departureDateTime = departureLocalDateTime.atZone(ZoneId.of(departureZone))
            val arrivalDateTime = arrivalLocalDateTime.atZone(ZoneId.of(arrivalZone))

            // Calculating flight duration
            val flightDuration = Duration.between(departureDateTime, arrivalDateTime)

            // Returning flight duration in format (hours and minutes)
            return "Duration: ${flightDuration.toHours()}h ${flightDuration.toMinutesPart()}min"
        }
        return "N/A"
    }


    fun getLatLngBoundsFromCameraPosition(cameraState:CameraState): LatLngBounds {
        // Centar of map
        val center = cameraState.center

        // Calculating map diameter in degrees based on current zoom level
        val latitudeSpan = 180.0 / 2.0.pow(cameraState.zoom)
        val longitudeSpan = 360.0 / 2.0.pow(cameraState.zoom)

        // Top left and bottom right angle
        val northWest =
            LatLng(center.latitude() + latitudeSpan / 2, center.longitude() - longitudeSpan / 2)
        val southEast =
            LatLng(center.latitude() - latitudeSpan / 2, center.longitude() + longitudeSpan / 2)

        return LatLngBounds.Builder()
            .include(northWest)
            .include(southEast)
            .build()
    }


    fun filterPlanesByVisibleRegion(planes:List<FlightResponse>,bounds: LatLngBounds): List<FlightResponse> {
        return planes.filter { plane ->
            bounds.contains(LatLng(plane.geography.latitude, plane.geography.longitude))
        }
    }

    fun capitalizeWords(input: String): String {
        return input.split(" ").joinToString(" ") { word ->
            word.lowercase().replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            }
        }
    }


}