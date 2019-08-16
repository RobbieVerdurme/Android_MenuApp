package com.example.boeferrob.menuapp.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
@Entity(tableName = "Ingredient"
)
data class Ingredient(
    var name: String,
    var quantity: Int,
    var measurement: String) : Parcelable{

    constructor() : this("", 0,"")

    @PrimaryKey(autoGenerate = true)
    var Ingredientkey: Int = 0
}