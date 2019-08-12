package com.example.boeferrob.menuapp.injection.component

import com.example.boeferrob.menuapp.App
import com.example.boeferrob.menuapp.injection.module.DatabaseModule
import com.example.boeferrob.menuapp.injection.module.NetworkModule
import com.example.boeferrob.menuapp.network.FoodRepository
import com.example.boeferrob.menuapp.ui.DecideViewModel
import com.example.boeferrob.menuapp.ui.FoodActivityViewModel
import com.example.boeferrob.menuapp.ui.FoodListFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun inject(application: App)
    fun inject(decideViewModel: DecideViewModel)
    fun inject(foodActivityViewModel: FoodActivityViewModel)
    fun inject(foodListFragmentViewModel: FoodListFragmentViewModel)
    fun inject(foodRepository: FoodRepository)

    @Component.Builder
    interface Builder{
        fun build(): AppComponent

        fun networkModule(networkModule: NetworkModule): Builder

        fun databaseModule(databaseModule: DatabaseModule) : Builder
    }
}