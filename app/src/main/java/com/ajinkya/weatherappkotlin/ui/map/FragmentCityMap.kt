package com.ajinkya.weatherappkotlin.ui.map

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ajinkya.weatherappkotlin.R
import com.ajinkya.weatherappkotlin.common.AppUtility
import com.ajinkya.weatherappkotlin.common.AppUtility.Companion.setSnackBar
import com.ajinkya.weatherappkotlin.di.DaggerComponentProvider
import com.ajinkya.weatherappkotlin.persistence.model.City
import com.ajinkya.weatherappkotlin.ui.home.CityListViewModelFactory
import com.ajinkya.weatherappkotlin.ui.home.CityViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*
import javax.inject.Inject

class FragmentCityMap : Fragment(), OnMapReadyCallback {

    private lateinit var mCity: City

    private val mComponent by lazy { DaggerComponentProvider.cityComponent() }

    @Inject
    lateinit var mViewModelFactory: CityListViewModelFactory

    private val mViewModel: CityViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory).get(CityViewModel::class.java)
    }

    private lateinit var mMapFragment: SupportMapFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mComponent.inject(this)

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadMap()
        initOnClickListeners()
    }

    /**
     * Initialize and load google map
     */
    private fun loadMap() {
        mMapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mMapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        setDefaultLocation(googleMap)
    }

    /**
     * set default location when maps get loaded initially
     * We have set Pune location as default for now
     */
    private fun setDefaultLocation(googleMap: GoogleMap?) {
        var latlang = LatLng(18.5204, 73.8567) // used Pune as default location for now
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlang, 10f))
        googleMap?.setOnCameraIdleListener {
            //get latitude longitude at the center of map , using the default marker location
            latlang = googleMap.cameraPosition.target
            if (latlang.latitude != 0.0 && latlang.longitude != 0.0) {
                getCityName(latlang)
            }
        }
    }

    /**
     * Extract City meta data using geoCoder Api
     * set selected city properties in object
     * if city is null, then clear the text
     * and set hint to move the marker
     */
    private fun getCityName(latLng: LatLng) {
        val geoCoder = Geocoder(requireActivity(), Locale.getDefault())
        val addressList = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        val cityName: String? = addressList?.get(0)?.locality
        val state: String = addressList[0].adminArea
        val country: String = addressList[0].countryName

        if (cityName != null) {
            textViewCityMapName.text = cityName
            val dateTime: String = AppUtility.getCurrentSystemDateTime()

            mCity = buildCityObject(
                cityName,
                latLng.latitude,
                latLng.longitude,
                state,
                country,
                dateTime
            )

        } else {
            mCity.title = ""
            textViewCityMapName.text = ""
            textViewCityMapName.hint = getString(R.string.move_marker)
        }
    }

    /**
     * initialize onClick listeners on views
     */
    private fun initOnClickListeners() {
        buttonCityMapBookmark?.setOnClickListener {
            if (mCity.title?.isNotEmpty() == true) {

                mViewModel.bookmarkCity(mCity)

                val cityBookmarked = String.format(
                    "%s %s", mCity.title, resources.getString(
                        R.string.city_added
                    )
                )
                setSnackBar(cityBookmarked, requireActivity())

            } else {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.move_marker_for_accuracy),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * Build and return city object based on passed parameters
     */
    private fun buildCityObject(
        title: String,
        latitude: Double,
        longitude: Double,
        state: String,
        country: String,
        dateTime: String
    ): City {
        return City(
            null,
            title,
            latitude,
            longitude,
            state,
            country,
            dateTime
        )
    }
}