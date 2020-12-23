package com.ajinkya.weatherappkotlin.ui.cityweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ajinkya.weatherappkotlin.data.remote.model.WeatherResponse
import com.ajinkya.weatherappkotlin.data.repository.interactor.CityWeatherRepository
import com.ajinkya.weatherappkotlin.usecase.CityWeatherUseCase
import com.ee.core.extension.toLiveData
import com.ee.core.networking.Outcome
import io.reactivex.disposables.CompositeDisposable

class CityWeatherViewModel(
    private val cityWeatherRepository: CityWeatherRepository.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel(), CityWeatherUseCase {

    val mCityWeatherResponse: LiveData<Outcome<WeatherResponse>> by lazy {
        cityWeatherRepository.cityWeatherResponse.toLiveData(compositeDisposable)
    }

    override fun getCityWeather(cityName: String) {
        cityWeatherRepository.getCityWeather(cityName)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}

