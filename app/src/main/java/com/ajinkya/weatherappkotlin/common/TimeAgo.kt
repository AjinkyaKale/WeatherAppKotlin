package com.ajinkya.weatherappkotlin.common

import android.content.Context
import com.ajinkya.weatherappkotlin.R

object TimeAgo {

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
//    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    fun getTimeAgo(context: Context, timeStamp: Long): String? {
        var time = timeStamp
        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> {
                context.getString(R.string.just_now)
            }
            diff < 2 * MINUTE_MILLIS -> {
                context.getString(R.string.a_minute_ago)
            }
            diff < 50 * MINUTE_MILLIS -> {
                val difference = diff / MINUTE_MILLIS
                context.getString(R.string.minutes_ago, difference)
            }
            diff < 90 * MINUTE_MILLIS -> {
                context.getString(R.string.an_hour_ago)
            }
            diff < 24 * HOUR_MILLIS -> {
                val difference = diff / HOUR_MILLIS
                context.getString(R.string.hours_ago, difference)
            }
            diff < 48 * HOUR_MILLIS -> {
                context.getString(R.string.yesterday)
            }
            else -> {
                AppUtility.getFormattedDate(time)
            }
        }
    }
}