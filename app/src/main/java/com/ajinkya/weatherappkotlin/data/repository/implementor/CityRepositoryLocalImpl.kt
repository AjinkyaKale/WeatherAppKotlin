package com.ajinkya.weatherappkotlin.data.repository.implementor

import com.ajinkya.weatherappkotlin.data.repository.interactor.CityRepository
import com.ajinkya.weatherappkotlin.persistence.dao.CityDao
import com.ajinkya.weatherappkotlin.persistence.model.City
import io.reactivex.Completable
import io.reactivex.Flowable

class CityRepositoryLocalImpl(
    private val cityDao: CityDao
) :
    CityRepository.Local {

    override fun getAllCities(): Flowable<List<City>> {
        return cityDao.getAllCities()
    }

    override fun bookmarkCity(city: City): Completable {
        return cityDao.insertCity(city)
    }

    override fun deleteCity(city: City): Completable {
        return cityDao.deleteCity(city)
    }
}