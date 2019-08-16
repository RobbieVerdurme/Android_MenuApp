package com.example.boeferrob.menuapp.RoomDatabase.Dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.boeferrob.menuapp.model.FoodIngredient

@Dao
interface FoodIngredientDao {
    @Insert
    fun insert(foodIngredient: FoodIngredient)

    @Update
    fun update(foodIngredient: FoodIngredient)

    @Delete
    fun delete(foodIngredient: FoodIngredient)

    @Query("SELECT IngredientKey FROM foodingredient WHERE foodKey = :foodkey")
    fun getIngredientForFood(foodkey: Int) : List<Int>
}