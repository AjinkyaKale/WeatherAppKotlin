package com.ajinkya.weatherappkotlin.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajinkya.weatherappkotlin.data.repository.interactor.CityRepository
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class CityListViewModelFactory(
    private val cityRepository: CityRepository.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CityViewModel(
            cityRepository,
            compositeDisposable
        ) as T
    }
}