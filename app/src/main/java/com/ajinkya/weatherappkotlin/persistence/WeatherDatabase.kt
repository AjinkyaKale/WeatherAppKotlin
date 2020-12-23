package com.ajinkya.weatherappkotlin.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ajinkya.weatherappkotlin.persistence.dao.CityDao
import com.ajinkya.weatherappkotlin.persistence.model.City


/**
 * The Room database that contains the below tables
 */
@Database(entities = [City::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java, "weather_database"
            )
                .build()
    }
}