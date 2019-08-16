package com.example.boeferrob.menuapp.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "FoodIngredient",
    primaryKeys = arrayOf("foodKey", "IngredientKey"),
    foreignKeys = arrayOf(
        ForeignKey(entity = Food::class,
            parentColumns = arrayOf("foodKey"),
            childColumns = arrayOf("foodKey")),
        ForeignKey(entity = Ingredient::class,
            parentColumns = arrayOf("Ingredientkey"),
            childColumns = arrayOf("IngredientKey"))
    ))
data class FoodIngredient(
    val foodKey: Int,
    val IngredientKey:Int) {}