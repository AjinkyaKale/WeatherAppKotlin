package com.ajinkya.weatherappkotlin.data.remote.service


import com.ajinkya.weatherappkotlin.data.remote.model.WeatherResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * All API calls will be handled from here
 */

interface WeatherService {

    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("q") cityName: String,
        @Query("appid") appId: String
    ): Flowable<WeatherResponse>
}

