package com.ajinkya.weatherappkotlin.data.repository.interactor

import com.ajinkya.weatherappkotlin.data.remote.model.CityResponse
import com.ajinkya.weatherappkotlin.persistence.model.City
import com.ee.core.networking.Outcome
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

interface CityRepository {

    interface Repository {

        val cityList: PublishSubject<Outcome<List<City>>>

        val cityResponse: PublishSubject<Outcome<CityResponse>>

        fun getAllCities()

        fun bookmarkCity(city: City)

        fun deleteCity(city: City)
    }

    interface Local {

        fun getAllCities(): Flowable<List<City>>

        fun bookmarkCity(city: City): Completable

        fun deleteCity(city: City): Completable
    }

}