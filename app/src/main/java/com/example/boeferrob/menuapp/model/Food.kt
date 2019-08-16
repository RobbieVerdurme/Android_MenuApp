package com.example.boeferrob.menuapp.model

import android.arch.persistence.room.*
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "Food")
@Parcelize
data class Food(
    var name: String,
    @Ignore
    var ingredients: MutableList<Ingredient> = mutableListOf(),
    var discritpion: String) : Parcelable  {

    constructor() : this("", ArrayList<Ingredient>(), "")

    @PrimaryKey(autoGenerate = true)
    var foodKey: Int = 0



    override fun toString(): String {
        return name
    }
}
