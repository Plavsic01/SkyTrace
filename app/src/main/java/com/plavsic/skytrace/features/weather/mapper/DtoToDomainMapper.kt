package com.plavsic.skytrace.features.weather.mapper

import com.plavsic.skytrace.features.weather.dto.CoordinateDTO
import com.plavsic.skytrace.features.weather.dto.SystemInfoDTO
import com.plavsic.skytrace.features.weather.dto.WeatherDTO
import com.plavsic.skytrace.features.weather.dto.WeatherInfoDTO
import com.plavsic.skytrace.features.weather.dto.WeatherResponseDTO
import com.plavsic.skytrace.features.weather.dto.WindDTO
import com.plavsic.skytrace.features.weather.model.Coordinate
import com.plavsic.skytrace.features.weather.model.SystemInfo
import com.plavsic.skytrace.features.weather.model.Weather
import com.plavsic.skytrace.features.weather.model.WeatherInfo
import com.plavsic.skytrace.features.weather.model.WeatherResponse
import com.plavsic.skytrace.features.weather.model.Wind

fun WeatherResponseDTO.toDomainModel():WeatherResponse{
    return WeatherResponse(
        coord = this.coord.toDomainModel(),
        weather = this.weather.toDomainModelList(),
        main = this.main.toDomainModel(),
        visibility = this.visibility,
        wind = this.wind.toDomainModel(),
        sys = this.sys.toDomainModel()
    )
}

fun List<WeatherDTO>.toDomainModelList():List<Weather>{
    return this.map { it.toDomainModel() }
}

fun CoordinateDTO.toDomainModel():Coordinate{
    return Coordinate(
        lon = this.lon,
        lat = this.lat
    )
}

fun WeatherDTO.toDomainModel():Weather{
    return Weather(
        main = this.main,
        description = this.description,
        icon = this.icon
    )
}

fun WeatherInfoDTO.toDomainModel():WeatherInfo{
    return WeatherInfo(
        temp = this.temp,
        feels_like = this.feels_like,
        temp_min = this.temp_min,
        temp_max = this.temp_max,
        pressure = this.pressure,
        humidity = this.humidity
    )
}

fun WindDTO.toDomainModel():Wind{
    return Wind(
        speed = this.speed,
        deg = this.deg
    )
}

fun SystemInfoDTO.toDomainModel():SystemInfo{
    return SystemInfo(
        sunrise = this.sunrise,
        sunset = this.sunset
    )
}