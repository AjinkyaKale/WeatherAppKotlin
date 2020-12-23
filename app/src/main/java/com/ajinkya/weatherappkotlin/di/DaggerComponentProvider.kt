package com.ajinkya.weatherappkotlin.di

import com.ajinkya.weatherappkotlin.di.component.AppComponent
import com.ajinkya.weatherappkotlin.di.component.CityComponent
import com.ajinkya.weatherappkotlin.di.component.CityWeatherComponent
import com.ajinkya.weatherappkotlin.di.component.DaggerAppComponent
import com.ajinkya.weatherappkotlin.di.module.CityModule
import com.ajinkya.weatherappkotlin.di.module.CityWeatherModule
import com.ee.core.application.CoreApp
import javax.inject.Singleton

@Singleton
object DaggerComponentProvider {

    private var mAppComponent: AppComponent? = null
    private var mCityComponent: CityComponent? = null
    private var mCityWeatherComponent: CityWeatherComponent? = null

    fun appComponent(): AppComponent {
        if (mAppComponent == null)
            mAppComponent =
                DaggerAppComponent.builder().coreComponent(CoreApp.coreComponent).build()
        return mAppComponent as AppComponent
    }

    fun cityComponent(): CityComponent {
        if (mCityComponent == null)
            mCityComponent = mAppComponent!!.addCityComponent(CityModule())
        return mCityComponent as CityComponent
    }

    fun cityWeatherComponent(): CityWeatherComponent {
        if (mCityWeatherComponent == null)
            mCityWeatherComponent = mAppComponent!!.addCityWeatherComponent(CityWeatherModule())
        return mCityWeatherComponent as CityWeatherComponent
    }

}