package com.ajinkya.weatherappkotlin.ui.cityweather


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajinkya.weatherappkotlin.data.repository.interactor.CityWeatherRepository
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class CityWeatherViewModelFactory(
    private val cityWeatherRepository: CityWeatherRepository.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CityWeatherViewModel(
            cityWeatherRepository,
            compositeDisposable
        ) as T
    }
}