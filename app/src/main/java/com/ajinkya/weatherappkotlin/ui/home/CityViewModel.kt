package com.ajinkya.weatherappkotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ajinkya.weatherappkotlin.data.repository.interactor.CityRepository
import com.ajinkya.weatherappkotlin.persistence.model.City
import com.ajinkya.weatherappkotlin.usecase.CityUseCase
import com.ee.core.extension.toLiveData
import com.ee.core.networking.Outcome
import io.reactivex.disposables.CompositeDisposable

class CityViewModel(
    private val cityRepository: CityRepository.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel(), CityUseCase {

    val mCityList: LiveData<Outcome<List<City>>> by lazy {
        cityRepository.cityList.toLiveData(compositeDisposable)
    }

    override fun getAllCities() {
        cityRepository.getAllCities()
    }

    override fun bookmarkCity(city: City) {
        cityRepository.bookmarkCity(city)
    }

    override fun deleteCity(city: City) {
        cityRepository.deleteCity(city)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}

