package com.plavsic.skytrace.utils.conversions

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.mapbox.maps.CameraState
import com.plavsic.skytrace.features.map.model.FlightResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

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


    fun formatTime(inputDate: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
        val date: Date? = inputDate?.let { inputFormat.parse(it) }

        // Format to AM/PM
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        return date?.let { outputFormat.format(it) }
    }



    fun getLatLngBoundsFromCameraPosition(cameraState:CameraState): LatLngBounds {
        // Centar mape
        val center = cameraState.center

        // Računamo prečnik mape u stepenima na osnovu trenutnog nivoa zumiranja
        val latitudeSpan = 180.0 / 2.0.pow(cameraState.zoom)
        val longitudeSpan = 360.0 / 2.0.pow(cameraState.zoom)

        // Odredimo gornji levi i donji desni ugao
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

}