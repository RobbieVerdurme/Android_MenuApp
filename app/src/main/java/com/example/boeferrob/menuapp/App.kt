package com.example.boeferrob.menuapp

import android.app.Application
import com.example.boeferrob.menuapp.injection.component.AppComponent
import com.example.boeferrob.menuapp.injection.component.DaggerAppComponent
import com.example.boeferrob.menuapp.injection.module.DatabaseModule
import com.example.boeferrob.menuapp.injection.module.NetworkModule

class App: Application(){
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .databaseModule(DatabaseModule(this))
            .networkModule(NetworkModule())
            .build()
    }
}