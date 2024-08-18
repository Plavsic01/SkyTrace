package com.plavsic.skytrace.model.data


data class Aircraft(
    val iataCode:String,
    val icaoCode:String,
    val icao24:String,
    val regNumber:String
)

data class Airline(
    val iataCode:String,
    val icaoCode:String
)

data class Arrival(
    val iataCode:String,
    val icaoCode:String
)

data class Departure(
    val iataCode:String,
    val icaoCode:String
)


data class Flight(
    val iataNumber:String,
    val icaoNumber:String,
    val number:String
)

data class Geography(
    val altitude:Double,
    val direction:Double,
    val latitude:Double,
    val longitude:Double,
)

data class Speed(
    val horizontal:Double,
    val isGround:Int
)

data class System(
    val updated:Int
)

data class FlightResponse(
    val aircraft: Aircraft,
    val airline: Airline,
    val arrival: Arrival,
    val departure: Departure,
    val flight: Flight,
    val geography: Geography,
    val speed: Speed,
    val status:String,
    val system: System
)



