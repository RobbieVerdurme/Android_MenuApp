package com.example.boeferrob.menuapp.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Ingredient(
    var name: String,
    var quantity: Int,
    var measurement: String) : Parcelable{

    constructor() : this("", 0,"") {}
}