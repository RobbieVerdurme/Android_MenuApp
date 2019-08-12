package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.App
import com.example.boeferrob.menuapp.model.Food
import com.example.boeferrob.menuapp.network.FoodRepository
import javax.inject.Inject

/**
 * the purpose foodlistFragementViewmodel is to provide the foodlist fragment with the data it needs
 */
class FoodListFragmentViewModel : ViewModel(){
    /************************************************variablen*********************************************************/
    @Inject
    lateinit var foodFoodRepository: FoodRepository
    private var foodList:LiveData<List<Food>>

    /***********************************************Methods***********************************************************/
    init {
        App.appComponent.inject(this)
        foodList = foodFoodRepository.getAllFood()
    }

    fun removeFood(removedItem : Food){
        foodFoodRepository.delete(removedItem)
    }

    fun saveFood(food: Food){
        foodFoodRepository.update(food)
    }

    /***********************************************get & set**********************************************************/
    fun getFoodList() : LiveData<List<Food>>{
        return foodList
    }
}