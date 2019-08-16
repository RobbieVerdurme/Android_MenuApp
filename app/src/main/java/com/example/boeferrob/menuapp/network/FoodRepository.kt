package com.example.boeferrob.menuapp.network

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.boeferrob.menuapp.App
import com.example.boeferrob.menuapp.Retrofit.FoodApi
import com.example.boeferrob.menuapp.RoomDatabase.Dao.FoodDao
import com.example.boeferrob.menuapp.RoomDatabase.Dao.FoodIngredientDao
import com.example.boeferrob.menuapp.RoomDatabase.Dao.IngredientDao
import com.example.boeferrob.menuapp.model.Food
import com.example.boeferrob.menuapp.model.FoodIngredient
import com.example.boeferrob.menuapp.model.Ingredient
import javax.inject.Inject

/**
 * this is the repository that communicates with the backend system
 * the repository is responsible for the get/update/set calls to the database
 */
class FoodRepository(private val foodDao: FoodDao, private val ingredientDao: IngredientDao, private val foodIngredientDao: FoodIngredientDao) {
    /******************************************VARIABLES***************************************************************/
    private var _allFood : LiveData<List<Food>>

    /**********************************$********METHODS****************************************************************/
    init {
        App.appComponent.inject(this)
        _allFood = foodDao.getallFood()
    }

    fun insert(food: Food) {
        foodDao.insert(food)
        for(ingredient in food.ingredients){
            ingredientDao.insert(ingredient)
            foodIngredientDao.insert(FoodIngredient(food.foodKey, ingredient.Ingredientkey))
        }
    }

    fun update(food: Food) {
        foodDao.update(food)
        for(ingredient in food.ingredients){
            ingredientDao.update(ingredient)
            foodIngredientDao.update(FoodIngredient(food.foodKey, ingredient.Ingredientkey))
        }
    }

    fun delete(food: Food) {
        foodDao.delete(food)
        for(ingredient in food.ingredients){
            ingredientDao.delete(ingredient)
            foodIngredientDao.delete(FoodIngredient(food.foodKey, ingredient.Ingredientkey))
        }
    }

    fun getAllFood(): LiveData<List<Food>> {
        if (_allFood.value == null){
            return _allFood
        }

        _allFood.value!!.forEach {
            val ingredientkeys = foodIngredientDao.getIngredientForFood(it.foodKey)
            for(ingredientKey in ingredientkeys){
                it.ingredients.add(ingredientDao.getIngredient(ingredientKey))
            }
        }
        return _allFood
    }
}
    /*
    object Repostory{
    private var liveFoodList :MutableLiveData<List<Food>> = MutableLiveData<List<Food>>()
    private var foodList = ArrayList<Food>()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRefrenceData = firebaseDatabase.getReference("FoodList")

    init {
        databaseRefrenceData.keepSynced(true)
        
        databaseRefrenceData.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                println("Failed to read value ${p0.message}")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                foodList.clear()
                for (h in dataSnapshot.child("Food").children){
                    val food = h.getValue(Food::class.java)
                    food?.key = h.key
                    foodList.add(food!!)
                }
                liveFoodList.value = foodList as List<Food>
            }
        })
    }

    fun getFoodList(): MutableLiveData<List<Food>>{
        if(liveFoodList.value == null){
            liveFoodList.value = foodList as List<Food>
        }
        return liveFoodList
    }

    fun remove(food: Food){
        databaseRefrenceData.child("Food").child(food.key!!).removeValue()
    }

    fun save(food : Food){
        if(food.key.isNullOrBlank()){
            databaseRefrenceData.child("Food").push().setValue(food)
        }else{
            databaseRefrenceData.child("Food").child(food.key!!).setValue(food)
        }
    }



    /////test
}
*/