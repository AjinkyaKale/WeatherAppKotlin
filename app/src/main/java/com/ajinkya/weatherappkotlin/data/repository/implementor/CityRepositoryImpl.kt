package com.ajinkya.weatherappkotlin.data.repository.implementor

import com.ajinkya.weatherappkotlin.data.remote.model.CityResponse
import com.ajinkya.weatherappkotlin.data.repository.interactor.CityRepository
import com.ajinkya.weatherappkotlin.persistence.model.City
import com.ee.core.extension.*
import com.ee.core.networking.Outcome
import com.ee.core.networking.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class CityRepositoryImpl(
    private val local: CityRepository.Local,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : CityRepository.Repository {

    override val cityList: PublishSubject<Outcome<List<City>>> by lazy {
        PublishSubject.create()
    }

    override val cityResponse: PublishSubject<Outcome<CityResponse>> by lazy {
        PublishSubject.create()
    }

    override fun getAllCities() {
        cityList.loading(true)

        local.getAllCities()
            .performOnBackOutOnMain(scheduler)
            .subscribe({ data ->
                cityList.success(data)
            }, { error ->
                cityList.failed(error)
            })
            .addTo(compositeDisposable)
    }

    override fun bookmarkCity(city: City) {
        local.bookmarkCity(city)
            .performOnBackOutOnMain(scheduler)
            .subscribe({
                cityResponse.success(
                    CityResponse.INSERT
                )
            }, { error ->
                cityResponse.failed(error)
            })
            .addTo(compositeDisposable)
    }

    override fun deleteCity(city: City) {
        local.deleteCity(city)
            .performOnBackOutOnMain(scheduler)
            .subscribe({
                cityResponse.success(
                    CityResponse.DELETE
                )
            }, { error ->
                cityResponse.failed(error)
            })
            .addTo(compositeDisposable)
    }
}
