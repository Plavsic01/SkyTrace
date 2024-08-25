package com.plavsic.skytrace.utils.conversions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs
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
        // Prvo parsiramo ulazni datum
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
        val date: Date? = inputDate?.let { inputFormat.parse(it) }

        // Formatiramo datum u željeni oblik
        val outputFormat = SimpleDateFormat("EEE, dd MMM", Locale.ENGLISH)
        return date?.let { outputFormat.format(it) }
    }


    fun formatTime(inputDate: String?): String? {
        // Prvo parsiramo ulazni datum
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
        val date: Date? = inputDate?.let { inputFormat.parse(it) }

        // Formatiramo vreme u AM/PM oblik
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        return date?.let { outputFormat.format(it) }
    }
}