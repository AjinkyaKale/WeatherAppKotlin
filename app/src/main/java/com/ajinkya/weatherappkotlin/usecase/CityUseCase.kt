package com.ajinkya.weatherappkotlin.usecase

import com.ajinkya.weatherappkotlin.persistence.model.City

interface CityUseCase {

    fun getAllCities()

    fun bookmarkCity(city: City)

    fun deleteCity(city: City)
}