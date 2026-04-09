package com.example.viewmodelexample

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET





interface GroceriesAPI {

    @GET("groceries")
    // http://localhost:8000/api/v1/groceries
    suspend fun getGroceries() : List<GroceryItem>
}






