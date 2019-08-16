package com.example.boeferrob.menuapp.Retrofit

import com.example.boeferrob.menuapp.model.Food
import retrofit2.http.GET
import retrofit2.http.Path
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * The interface which provides methods to get result of the Post webservice
 */
interface FoodApi {

    /**
     * Get a food object form the API
     */
    @GET("/api/Food/{id}")
    fun getFood(@Path("id") id: String) : Observable<Food>

    /**
     * Get a food list form the API
     */
    @GET("/api/Food")
    fun getAllFood() : Observable<List<Food>>
}