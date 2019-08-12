package com.example.boeferrob.menuapp.RoomDatabase.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.boeferrob.menuapp.RoomDatabase.Dao.FoodDao
import com.example.boeferrob.menuapp.model.Food

@Database(entities = arrayOf(Food::class), version = 1)
abstract class FoodDatabase : RoomDatabase() {
     abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var instance : FoodDatabase? = null

        fun getInstance(context: Context) :FoodDatabase {

            if(instance == null){
                synchronized(context){
                    instance = Room.databaseBuilder(context.applicationContext,
                        FoodDatabase::class.java, "Food")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }

        fun destroyInstance(){
            instance = null
        }

    }
}