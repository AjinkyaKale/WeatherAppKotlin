package com.ajinkya.weatherappkotlin.ui.cityweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ajinkya.weatherappkotlin.R
import com.ajinkya.weatherappkotlin.common.Constants
import com.ajinkya.weatherappkotlin.data.remote.model.WeatherResponse
import com.ajinkya.weatherappkotlin.di.DaggerComponentProvider
import com.ee.core.extension.toast
import com.ee.core.networking.Outcome
import kotlinx.android.synthetic.main.weather_details_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentCityWeather : Fragment() {

    private val mComponent by lazy { DaggerComponentProvider.cityWeatherComponent() }

    @Inject
    lateinit var mViewModelFactory: CityWeatherViewModelFactory

    private val mViewModel: CityWeatherViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory).get(CityWeatherViewModel::class.java)
    }

    private var mView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mView = inflater.inflate(R.layout.weather_details_fragment, container, false)

        mComponent.inject(this)

        initObservers()

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCityDetails()

        CoroutineScope(Dispatchers.Main).launch {
            getCityWeatherDetails()
        }
    }

    private fun getCityWeatherDetails() {
        mViewModel.getCityWeather(txtCityNameWeatherDetails.text.toString())
    }

    /**
     * get arguments from bundle
     * for which user is interested to see weather
     * showing city name on top
     */
    private fun setCityDetails() {
        txtCityNameWeatherDetails.text = arguments?.getString(Constants.ARGS_CITY_NAME)
    }

    /**
     * give a call to load weather from network
     */
    private fun initObservers() {
        mViewModel.mCityWeatherResponse.observe(
            requireActivity(),
            { outcome ->
                when (outcome) {
                    is Outcome.Progress -> {
                        //Todo show loading indicator
                    }
                    is Outcome.Success -> {
                        showWeatherDetails(outcome.data)
                    }
                    is Outcome.Failure -> {
                        requireActivity().toast(getString(R.string.something_went_wrong))
                    }
                }
            })
    }

    /**
     * set details from response to view
     */
    private fun showWeatherDetails(weatherResponse: WeatherResponse) {
        txtTemperatureWeatherDetails.text =
            String.format(
                "%s  %s", resources.getString(R.string.temperature),
                weatherResponse.main?.temperature.toString()
            )

        txtHumidityWeatherDetails.text =
            String.format(
                "%s  %s", resources.getString(R.string.humidity),
                weatherResponse.main?.humidity.toString()
            )

        txtWindWeatherDetails.text =
            String.format(
                "%s  %s %s", resources.getString(R.string.wind),
                weatherResponse.wind?.speed, resources.getString(R.string.speed_measure)
            )
    }
}