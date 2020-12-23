package com.ajinkya.weatherappkotlin.di.component

import com.ajinkya.weatherappkotlin.di.PerActivity
import com.ajinkya.weatherappkotlin.di.module.CityModule
import com.ajinkya.weatherappkotlin.ui.home.FragmentCityList
import com.ajinkya.weatherappkotlin.ui.map.FragmentCityMap
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [CityModule::class])
interface CityComponent {

    fun inject(fragmentCityList: FragmentCityList)

    fun inject(fragmentCityMap: FragmentCityMap)
}