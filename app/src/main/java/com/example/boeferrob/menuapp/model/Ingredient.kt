package com.example.boeferrob.menuapp.model

import java.io.Serializable

data class Ingredient(var name: String, var quantity: Int, var measurement: String) : Serializable{
    constructor() : this("", 0,"") {}
}