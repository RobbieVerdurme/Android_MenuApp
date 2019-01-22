package com.example.boeferrob.menuapp.network

import android.arch.lifecycle.MutableLiveData
import com.example.boeferrob.menuapp.model.Food
import com.google.firebase.database.*
import java.util.ArrayList

/**
 * this is the repository that communicates with the backend system
 * the repository is responsible for the get/update/set calls to the database
 */
object Repository {
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