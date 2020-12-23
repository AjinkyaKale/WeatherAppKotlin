package com.ajinkya.weatherappkotlin.common

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.ajinkya.weatherappkotlin.R
import com.google.android.material.snackbar.Snackbar

fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}

fun Toolbar.changeToolbarFont() {
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is AppCompatTextView && view.text == title) {
            view.typeface = ResourcesCompat.getFont(context, R.font.opensans_regular)

            break
        }
    }
}