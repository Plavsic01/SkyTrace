package com.plavsic.skytrace.utils.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.plavsic.skytrace.features.futureFlights.dto.AirlineDTO
import com.plavsic.skytrace.features.schedule.dto.AirlineDetailsDTO
import java.lang.reflect.Type

class AirlineDetailsDeserializer:JsonDeserializer<AirlineDetailsDTO> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): AirlineDetailsDTO {
        val jsonObject = json.asJsonObject

        val airline = AirlineDTO(
            name = jsonObject.get("name").asString,
            iataCode = jsonObject.get("iataCode").asString
        )

        val name = jsonObject.get("name").asString

        return AirlineDetailsDTO(airline,name)
    }

}