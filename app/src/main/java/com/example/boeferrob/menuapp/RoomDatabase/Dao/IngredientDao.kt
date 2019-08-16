package com.example.boeferrob.menuapp.RoomDatabase.Dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.boeferrob.menuapp.model.Ingredient

@Dao
interface IngredientDao {
    @Insert
    fun insert(ingredient: Ingredient)

    @Update
    fun update(ingredient: Ingredient)

    @Delete
    fun delete(ingredient: Ingredient)

    @Query("SELECT * FROM Ingredient WHERE Ingredientkey = :ingredientKey")
    fun getIngredient(ingredientKey: Int): Ingredient
}