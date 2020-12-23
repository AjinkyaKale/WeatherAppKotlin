package com.ajinkya.weatherappkotlin.data.remote.model

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lon")
    var lon: Float = 0.toFloat(),
    @SerializedName("lat")
    var lat: Float = 0.toFloat()
)