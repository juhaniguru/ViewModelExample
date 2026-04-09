package com.example.viewmodelexample

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class GroceriesApp : Application() {


    private val api = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/api/v1/")
        .addConverterFactory(GsonConverterFactory.create()).build()


    val groceriesAPI by lazy { api.create<GroceriesAPI>() }

}