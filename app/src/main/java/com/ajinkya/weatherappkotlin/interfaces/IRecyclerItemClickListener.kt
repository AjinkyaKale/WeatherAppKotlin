package com.ajinkya.weatherappkotlin.interfaces

import android.view.View

interface IRecyclerItemClickListener<T> {
    fun onClickCity(view: View, data: T)
    fun onClickDelete(data: T)
}