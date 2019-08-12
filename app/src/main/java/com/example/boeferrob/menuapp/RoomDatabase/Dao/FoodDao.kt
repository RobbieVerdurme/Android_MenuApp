package com.example.boeferrob.menuapp.RoomDatabase.Dao

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*
import com.example.boeferrob.menuapp.model.Food

@Dao
interface FoodDao {

    @Insert
    fun insert(food : Food)

    @Update
    fun update(food: Food)

    @Delete
    fun delete(food: Food)

    @Query("SELECT * FROM Food ORDER BY Name")
    fun getallFood() : LiveData<List<Food>>

}