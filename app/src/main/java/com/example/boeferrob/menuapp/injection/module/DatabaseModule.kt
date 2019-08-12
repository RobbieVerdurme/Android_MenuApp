package com.example.boeferrob.menuapp.injection.module

import android.app.Application
import android.content.Context
import com.example.boeferrob.menuapp.RoomDatabase.Dao.FoodDao
import com.example.boeferrob.menuapp.RoomDatabase.Database.FoodDatabase
import com.example.boeferrob.menuapp.network.FoodRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val application: Application) {
    @Provides
    @Singleton
    internal fun provideFoodDao(foodDatabase: FoodDatabase) : FoodDao {
        return foodDatabase.foodDao()
    }

    @Provides
    @Singleton
    internal fun provideApplicationContext() : Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideFoodDatabase(context: Context):FoodDatabase{
        return FoodDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    internal fun provideFoodRepository(foodDao: FoodDao): FoodRepository {
        return FoodRepository(foodDao)
    }
}