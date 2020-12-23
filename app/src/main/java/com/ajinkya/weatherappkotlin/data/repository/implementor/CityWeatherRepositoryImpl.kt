package com.ajinkya.weatherappkotlin.data.repository.implementor

import com.ajinkya.weatherappkotlin.data.remote.model.WeatherResponse
import com.ajinkya.weatherappkotlin.data.repository.interactor.CityWeatherRepository
import com.ee.core.extension.*
import com.ee.core.networking.Outcome
import com.ee.core.networking.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class CityWeatherRepositoryImpl(
    private val remote: CityWeatherRepository.Remote,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : CityWeatherRepository.Repository {

    override val cityWeatherResponse: PublishSubject<Outcome<WeatherResponse>> by lazy {
        PublishSubject.create()
    }

    override fun getCityWeather(cityName: String) {
        cityWeatherResponse.loading(true)

        remote.getCityWeather(cityName)
            .performOnBackOutOnMain(scheduler)
            .subscribe({ data ->
                cityWeatherResponse.success(data)
            }, { error ->
                cityWeatherResponse.failed(error)
            })
            .addTo(compositeDisposable)
    }
}
