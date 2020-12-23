package com.ajinkya.weatherappkotlin.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord")
    var coord: Coord? = null,
    @SerializedName("weather")
    var weather: ArrayList<Weather> = ArrayList<Weather>(),
    @SerializedName("main")
    var main: Main? = null,
    @SerializedName("wind")
    var wind: Wind? = null
)