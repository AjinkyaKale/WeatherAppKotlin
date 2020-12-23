package com.ajinkya.weatherappkotlin.di.component

import com.ajinkya.weatherappkotlin.MainActivity
import com.ajinkya.weatherappkotlin.WeatherAppApplication
import com.ajinkya.weatherappkotlin.common.SharedPreferenceHelper
import com.ajinkya.weatherappkotlin.data.remote.service.WeatherService
import com.ajinkya.weatherappkotlin.di.AppScope
import com.ajinkya.weatherappkotlin.di.module.AppModule
import com.ajinkya.weatherappkotlin.di.module.CityModule
import com.ajinkya.weatherappkotlin.di.module.CityWeatherModule
import com.ee.core.di.CoreComponent
import dagger.Component


@AppScope
@Component(dependencies = [CoreComponent::class], modules = [AppModule::class])
interface AppComponent {

    fun sharedPreferenceHelper(): SharedPreferenceHelper
    fun weatherService(): WeatherService

    fun inject(application: WeatherAppApplication)
    fun inject(mainActivity: MainActivity)

    // sub-components
    fun addCityComponent(cityModule: CityModule): CityComponent
    fun addCityWeatherComponent(cityWeatherModule: CityWeatherModule): CityWeatherComponent
}