package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.*
import com.example.boeferrob.menuapp.App
import com.example.boeferrob.menuapp.model.Food
import com.example.boeferrob.menuapp.network.FoodRepository
import javax.inject.Inject

/**
 * the purpose foodactivityviewmodel is to provide the foodactivity with the data it needs
 */
class FoodActivityViewModel : ViewModel() {
    /************************************************variablen*********************************************************/
    @Inject
    lateinit var foodFoodRepository: FoodRepository
    private var foodList: LiveData<List<Food>>

    /************************************************Methods***********************************************************/
    init {
        App.appComponent.inject(this)
        foodList = foodFoodRepository.getAllFood()
    }

    fun addFood(food: Food){
        foodFoodRepository.insert(food)
    }

    fun saveFood(food: Food){
        foodFoodRepository.update(food)
    }

    /***********************************************get & set**********************************************************/
    fun getFood(index: Int): Food {
        return foodList.value?.get(index)!!
    }
}