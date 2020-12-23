package com.ajinkya.weatherappkotlin.data.repository.interactor

import com.ajinkya.weatherappkotlin.data.remote.model.WeatherResponse
import com.ee.core.networking.Outcome
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

interface CityWeatherRepository {

    interface Repository {
        val cityWeatherResponse: PublishSubject<Outcome<WeatherResponse>>

        fun getCityWeather(cityName: String)
    }

    interface Remote {

        fun getCityWeather(cityName: String): Flowable<WeatherResponse>
    }

}