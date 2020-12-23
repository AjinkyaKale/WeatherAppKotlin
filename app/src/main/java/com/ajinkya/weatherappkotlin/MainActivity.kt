package com.ajinkya.weatherappkotlin

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ajinkya.weatherappkotlin.common.AppUtility
import com.ajinkya.weatherappkotlin.common.changeToolbarFont
import com.ajinkya.weatherappkotlin.di.DaggerComponentProvider
import com.ajinkya.weatherappkotlin.ui.home.FragmentCityList
import com.ajinkya.weatherappkotlin.ui.map.FragmentCityMap
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val mComponent by lazy { DaggerComponentProvider.appComponent() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        AppUtility.changeNavBarColorAccordingToTheme(this);

        mComponent.inject(this)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        setToolBar(getString(R.string.app_name))

        addFragment(FragmentCityList())
    }

    /**
     * Function to set the custom toolbar and it's properties
     */
    fun setToolBar(title: String?) {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        toolbar.setTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.textTitleColor
            )
        )
        toolbar.changeToolbarFont()
    }

    /**
     * Navigation listener for bottom navigation menu
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(FragmentCityList())
                return true
            }
            R.id.navigation_notes -> {
                replaceFragment(FragmentCityMap())
                return true
            }
            R.id.navigation_settings -> {
                //Todo add settings fragment
                return true
            }
        }
        return false
    }

    /**
     * Function to replace the fragment passed as an argument into the container.
     */
    fun replaceFragment(fragment: Fragment) {
        val mTag = fragment.javaClass.name

        val fragmentInstance: Fragment? = supportFragmentManager.findFragmentByTag(mTag)
        if (fragmentInstance != null && fragmentInstance.isVisible) {
            return
        } else {
            val fragmentTransaction =
                supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayoutContainer, fragment, mTag)
            fragmentTransaction.commit()
        }
    }

    /**
     * Function to add the fragment passed as an argument into the container.
     */
    fun addFragment(fragment: Fragment) {
        val mTag = fragment.javaClass.name

        val fragmentInstance: Fragment? = supportFragmentManager.findFragmentByTag(mTag)
        if (fragmentInstance != null && fragmentInstance.isVisible) {
            return
        } else {
            val fragmentTransaction =
                supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.frameLayoutContainer, fragment, mTag)
            fragmentTransaction.addToBackStack(mTag)
            fragmentTransaction.commit()
        }
    }

    /**
     * Pop the fragment if back stack count is greater than 1.
     * else finish the activity.
     */
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
            if (supportFragmentManager.backStackEntryCount == 1) {
                setToolBar(getString(R.string.app_name))
            }
        } else {
            finish()
        }
    }
}