package com.plavsic.skytrace.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://aviation-edge.com/v2/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: FlightService by lazy {
        retrofit.create(FlightService::class.java)
    }
}
