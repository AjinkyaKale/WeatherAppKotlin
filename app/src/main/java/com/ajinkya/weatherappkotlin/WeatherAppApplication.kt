package com.ajinkya.weatherappkotlin

import android.content.SharedPreferences
import androidx.lifecycle.LifecycleObserver

import com.ajinkya.weatherappkotlin.common.Constants
import com.ajinkya.weatherappkotlin.common.ThemeHelper
import com.ajinkya.weatherappkotlin.di.DaggerComponentProvider
import com.ee.core.application.CoreApp

class WeatherAppApplication : CoreApp(), LifecycleObserver {

    private val component by lazy { DaggerComponentProvider.appComponent() }

    override fun onCreate() {

        super.onCreate()

        initDI(getBaseUrl())

        component.inject(this)

        setUpTheme()
    }

    private fun setUpTheme() {
        val sharedPreferences: SharedPreferences =
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        val themePref =
            sharedPreferences.getString(Constants.THEME_PREF, ThemeHelper.DEFAULT_MODE)
        ThemeHelper.applyTheme(themePref!!)
    }

    private fun getBaseUrl(): String {

        return BuildConfig.BASE_URL
    }

}