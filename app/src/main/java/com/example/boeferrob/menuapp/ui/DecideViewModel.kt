package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.App
import com.example.boeferrob.menuapp.model.Food
import com.example.boeferrob.menuapp.network.FoodRepository
import com.example.boeferrob.menuapp.utils.POSITION_NOT_SET
import javax.inject.Inject
import kotlin.random.Random

/**
 * the purpose decideViewmodel is to provide the decide fragment with the data it needs
 */
class DecideViewModel : ViewModel(){
    /************************************************variablen*********************************************************/
    @Inject
    lateinit var foodFoodRepository: FoodRepository
    private var foodList:LiveData<List<Food>>
    private var chosenRandomfood: Food? = null

    /************************************************Methods***********************************************************/
    init {
        App.appComponent.inject(this)

        foodList = foodFoodRepository.getAllFood()
    }

    fun RandomFood(): String {
        if(foodList.value.isNullOrEmpty()){
            return "No food in list. Please add food"
        }else{
            val random = Random
            val randomId = random.nextInt(foodList.value!!.size)
            chosenRandomfood = foodList.value!!.get(randomId)
            return chosenRandomfood!!.name
        }
    }

    fun getFoodIndex(): Int{
        if(chosenRandomfood == null){
            return POSITION_NOT_SET
        }else {
            return foodList.value!!.indexOf(chosenRandomfood!!)
        }
    }
}