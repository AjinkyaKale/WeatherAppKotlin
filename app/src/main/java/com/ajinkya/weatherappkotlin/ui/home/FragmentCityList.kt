package com.ajinkya.weatherappkotlin.ui.home


import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajinkya.weatherappkotlin.MainActivity
import com.ajinkya.weatherappkotlin.R
import com.ajinkya.weatherappkotlin.common.Constants
import com.ajinkya.weatherappkotlin.di.DaggerComponentProvider
import com.ajinkya.weatherappkotlin.interfaces.IRecyclerItemClickListener
import com.ajinkya.weatherappkotlin.persistence.model.City
import com.ajinkya.weatherappkotlin.ui.cityweather.FragmentCityWeather
import com.ee.core.extension.toast
import com.ee.core.networking.Outcome
import com.google.android.material.transition.platform.MaterialFadeThrough
import kotlinx.android.synthetic.main.fragment_city_list.*
import kotlinx.android.synthetic.main.fragment_city_list.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentCityList : Fragment(), IRecyclerItemClickListener<City> {

    private val mComponent by lazy { DaggerComponentProvider.cityComponent() }

    @Inject
    lateinit var mViewModelFactory: CityListViewModelFactory

    private val mViewModel: CityViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory).get(CityViewModel::class.java)
    }

    private var mCityAdapter: CityAdapter? = null

    private lateinit var mCityList: List<City>

    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            enterTransition = MaterialFadeThrough()
            exitTransition = MaterialFadeThrough()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_city_list, container, false)

        mComponent.inject(this)

        setHasOptionsMenu(true)

        initObserver()

        return mView
    }

    private fun initObserver() {

        mCityList = listOf()

        mViewModel.mCityList.observe(
            requireActivity(),
            { outcome ->
                when (outcome) {
                    is Outcome.Progress -> {
                        //Todo show loading indicator
                    }
                    is Outcome.Success -> {
                        buildCityList(outcome.data)
                        setNoHistoryTextVisibility()
                    }
                    is Outcome.Failure -> {
                        requireActivity().toast(getString(R.string.something_went_wrong))
                    }
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity?)!!.setToolBar(getString(R.string.city_toolbar_title))

        setUpRecyclerViewAdapter()

        CoroutineScope(Dispatchers.Main).launch {
            getCitiesList()
        }
    }

    private fun getCitiesList() {
        mViewModel.getAllCities()
    }

    private fun setUpRecyclerViewAdapter() {
        mCityAdapter = CityAdapter(requireActivity(), this)
        recyclerViewCityList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mCityAdapter
        }
    }

    private fun buildCityList(list: List<City>) {
        mCityList = list
        mCityAdapter!!.modifyList(mCityList)
    }

    private fun setNoHistoryTextVisibility() {
        if (mCityList.isEmpty()) {
            mView.textViewCityListHistoryText.visibility = View.VISIBLE
        } else {
            mView.textViewCityListHistoryText.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_city_list, menu)
        setUpSearchView(menu)
    }

    private fun setUpSearchView(menu: Menu) {
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mCityAdapter!!.filter(newText)
                return true
            }
        })
    }

    override fun onClickCity(view: View, data: City) {
        launchWeatherDetailsFragment(view, data)
    }

    private fun launchWeatherDetailsFragment(view: View, data: City?) {
        val fragmentCityWeather = FragmentCityWeather()
        val bundle = Bundle()
        bundle.putString(Constants.ARGS_CITY_NAME, data?.title)
        fragmentCityWeather.arguments = bundle
        (activity as MainActivity).addFragment(fragmentCityWeather)
    }

    override fun onClickDelete(data: City) {
        mViewModel.deleteCity(data)
    }
}
