package com.ajinkya.weatherappkotlin.di.module

import android.content.SharedPreferences
import com.ajinkya.weatherappkotlin.common.SharedPreferenceHelper
import com.ajinkya.weatherappkotlin.data.remote.service.WeatherService
import com.ajinkya.weatherappkotlin.di.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class AppModule {

    @AppScope
    @Provides
    fun provideSharedPref(sharedPreferences: SharedPreferences): SharedPreferenceHelper =
        SharedPreferenceHelper(sharedPreferences)

    @AppScope
    @Provides
    fun weatherService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)
}