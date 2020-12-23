package com.ajinkya.weatherappkotlin.di.component

import com.ajinkya.weatherappkotlin.di.PerActivity
import com.ajinkya.weatherappkotlin.di.module.CityWeatherModule
import com.ajinkya.weatherappkotlin.ui.cityweather.FragmentCityWeather
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [CityWeatherModule::class])
interface CityWeatherComponent {
    fun inject(fragmentCityWeather: FragmentCityWeather)
}