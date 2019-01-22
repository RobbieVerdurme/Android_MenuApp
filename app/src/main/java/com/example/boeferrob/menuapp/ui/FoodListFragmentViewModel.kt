package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.model.Food
import com.example.boeferrob.menuapp.network.Repository

/**
 * the purpose foodlistFragementViewmodel is to provide the foodlist fragment with the data it needs
 */
class FoodListFragmentViewModel:ViewModel(){
    /************************************************variablen*********************************************************/
    private var foodList: MutableLiveData<List<Food>> = Repository.getFoodList()

    /***********************************************Methods***********************************************************/
    fun removeFood(removedItem : Food){
        Repository.remove(removedItem)
    }

    fun saveFood(food: Food){
        Repository.save(food)
    }

    /***********************************************get & set**********************************************************/
    fun getFoodList() : LiveData<List<Food>>{
        return foodList as LiveData<List<Food>>
    }
}