package com.ajinkya.weatherappkotlin.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "city")
data class City(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "latitude")
    var latitude: Double?,

    @ColumnInfo(name = "longitude")
    var longitude: Double?,

    @ColumnInfo(name = "state")
    var state: String?,

    @ColumnInfo(name = "country")
    var country: String?,

    @ColumnInfo(name = "date")
    var dateTime: String?

) : Serializable