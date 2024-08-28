package com.plavsic.skytrace.utils

enum class WeekDays(
    val weekDay:String
) {
    MONDAY("1"),
    TUESDAY("2"),
    WEDNESDAY("3"),
    THURSDAY("4"),
    FRIDAY("5"),
    SATURDAY("6"),
    SUNDAY("7");

    companion object {
        fun fromDayNumber(dayNumber:String): WeekDays? {
            return entries.find {
                it.weekDay == dayNumber
            }
        }
    }
}