package com.ajinkya.weatherappkotlin.data.repository.implementor

import com.ajinkya.weatherappkotlin.BuildConfig
import com.ajinkya.weatherappkotlin.data.remote.model.WeatherResponse
import com.ajinkya.weatherappkotlin.data.remote.service.WeatherService
import com.ajinkya.weatherappkotlin.data.repository.interactor.CityWeatherRepository
import io.reactivex.Flowable

class CityWeatherRepositoryRemoteImpl(
    private val weatherService: WeatherService
) :
    CityWeatherRepository.Remote {

    override fun getCityWeather(cityName: String): Flowable<WeatherResponse> {
        return weatherService.getCurrentWeatherData(cityName, BuildConfig.APP_ID)
    }
}