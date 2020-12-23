package com.ajinkya.weatherappkotlin.di.module


import android.content.Context
import com.ajinkya.weatherappkotlin.data.repository.implementor.CityRepositoryImpl
import com.ajinkya.weatherappkotlin.data.repository.implementor.CityRepositoryLocalImpl
import com.ajinkya.weatherappkotlin.data.repository.interactor.CityRepository
import com.ajinkya.weatherappkotlin.di.PerActivity
import com.ajinkya.weatherappkotlin.persistence.WeatherDatabase
import com.ajinkya.weatherappkotlin.persistence.dao.CityDao
import com.ajinkya.weatherappkotlin.ui.home.CityListViewModelFactory
import com.ee.core.networking.Scheduler
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class CityModule {

    @Provides
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @PerActivity
    fun provideCityListViewModelFactory(
        repository: CityRepository.Repository,
        compositeDisposable: CompositeDisposable
    ): CityListViewModelFactory =
        CityListViewModelFactory(
            repository,
            compositeDisposable
        )

    @Provides
    @PerActivity
    fun provideCityRepository(
        local: CityRepository.Local, scheduler: Scheduler,
        compositeDisposable: CompositeDisposable
    ): CityRepository.Repository =
        CityRepositoryImpl(local, scheduler, compositeDisposable)

    @Provides
    @PerActivity
    fun provideCityLocal(cityDao: CityDao): CityRepository.Local =
        CityRepositoryLocalImpl(cityDao)

    @Provides
    @PerActivity
    fun provideCityDataSource(context: Context): CityDao {
        val database = WeatherDatabase.getInstance(context)
        return database.cityDao()
    }

}