package com.ajinkya.weatherappkotlin.common

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.ajinkya.weatherappkotlin.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


class AppUtility {

    companion object {

        private const val DATE_WITH_TIME = "dd MMM yy, hh:mm a"

        fun getFormattedDate(timestamp: Long): String {
            val sf = SimpleDateFormat(DATE_WITH_TIME, Locale.getDefault())
            val date = Date(timestamp)
            return sf.format(date)
        }

        fun getCurrentSystemDateTime(): String {
            val tsLong = System.currentTimeMillis()
            return tsLong.toString()
        }

        fun changeNavBarColorAccordingToTheme(activity: Activity) {
            val sharedPreferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(activity)
            val themePref =
                sharedPreferences.getString(Constants.THEME_PREF, ThemeHelper.DEFAULT_MODE)

            when (themePref) {
                ThemeHelper.LIGHT_MODE -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        activity.window?.decorView?.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                ThemeHelper.DARK_MODE -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        activity.window?.decorView?.systemUiVisibility = 0
                }
                else -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        activity.window?.decorView?.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }

        fun setSnackBar(snackBarText: String, activity: Activity) {
            val viewGroup: ViewGroup =
                (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

            val bar = Snackbar.make(viewGroup, snackBarText, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(activity, R.color.secondary))
                .setBackgroundTint(ContextCompat.getColor(activity, R.color.snackBarBackground))
                .setAction("Dismiss") { }
            val textView =
                bar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTextColor(Color.WHITE)
            bar.show()
        }
    }
}

