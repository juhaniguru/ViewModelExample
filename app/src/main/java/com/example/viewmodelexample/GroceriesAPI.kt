package com.example.viewmodelexample

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface GroceriesAPI {

    @GET("groceries")
    // http://localhost:8000/api/v1/groceries
    suspend fun getGroceries(): List<GroceryItem>

    @POST("groceries")
    suspend fun createGroceries(@Body reqData: CreateGroceriesReqDto): GroceryItem

    @GET("/api/v1/groceries/expenses/{name}")
    suspend fun getDetails(
        @Path("name") name: String,

        ): List<DetailDataPoint>
}






