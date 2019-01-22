package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.model.Food
import com.example.boeferrob.menuapp.network.Repository

/**
 * the purpose foodactivityviewmodel is to provide the foodactivity with the data it needs
 */
class FoodActivityViewModel: ViewModel() {
    /************************************************variablen*********************************************************/
    private var foodList: MutableLiveData<List<Food>> = Repository.getFoodList()

    /************************************************Methods***********************************************************/
    fun getLastIndexFood(): Int{
        return  foodList.value!!.size
    }

    fun addFood(food: Food){
        val foodListArrayList: ArrayList<Food>? = foodList.value as ArrayList<Food>
        foodListArrayList!!.add(food)
        foodList.value = foodListArrayList
    }

    fun saveFood(food: Food){
        Repository.save(food)
    }

    /***********************************************get & set**********************************************************/
    fun getFood(index: Int): Food {
        return foodList.value?.get(index)!!
    }
}