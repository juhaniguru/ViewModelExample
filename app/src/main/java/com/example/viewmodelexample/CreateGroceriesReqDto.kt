package com.example.viewmodelexample

import com.google.gson.annotations.SerializedName

data class CreateGroceriesReqDto(
    val name: String,
    @SerializedName("item_count")
    val itemCount: Int
)
