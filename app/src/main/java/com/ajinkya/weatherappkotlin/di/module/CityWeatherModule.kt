package com.ajinkya.weatherappkotlin.di.module


import com.ajinkya.weatherappkotlin.data.remote.service.WeatherService
import com.ajinkya.weatherappkotlin.data.repository.implementor.CityWeatherRepositoryImpl
import com.ajinkya.weatherappkotlin.data.repository.implementor.CityWeatherRepositoryRemoteImpl
import com.ajinkya.weatherappkotlin.data.repository.interactor.CityWeatherRepository
import com.ajinkya.weatherappkotlin.di.PerActivity
import com.ajinkya.weatherappkotlin.ui.cityweather.CityWeatherViewModelFactory
import com.ee.core.networking.Scheduler
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class CityWeatherModule {

    @Provides
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @PerActivity
    fun provideCityWeatherViewModelFactory(
        repository: CityWeatherRepository.Repository,
        compositeDisposable: CompositeDisposable
    ): CityWeatherViewModelFactory =
        CityWeatherViewModelFactory(
            repository,
            compositeDisposable
        )

    @Provides
    @PerActivity
    fun provideCityWeatherRepository(
        remote: CityWeatherRepository.Remote, scheduler: Scheduler,
        compositeDisposable: CompositeDisposable
    ): CityWeatherRepository.Repository =
        CityWeatherRepositoryImpl(remote, scheduler, compositeDisposable)

    @Provides
    @PerActivity
    fun provideCityWeatherRemote(weatherService: WeatherService): CityWeatherRepository.Remote =
        CityWeatherRepositoryRemoteImpl(weatherService)
}