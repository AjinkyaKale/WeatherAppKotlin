package com.ajinkya.weatherappkotlin.persistence.dao

import androidx.room.*
import com.ajinkya.weatherappkotlin.persistence.model.City
import io.reactivex.Completable
import io.reactivex.Flowable


@Dao
interface CityDao {

    @Query("SELECT * FROM city ORDER BY date DESC")
    fun getAllCities(): Flowable<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City): Completable

    @Delete()
    fun deleteCity(city: City): Completable
}