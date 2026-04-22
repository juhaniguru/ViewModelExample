package com.example.viewmodelexample

import com.google.gson.annotations.SerializedName

data class GroceriesState(
    val loading: Boolean = false,
    val error: String? = null,
    val groceries: List<GroceryItem> = emptyList()
)

data class AddGroceriesState(
    val name: String = "",
    val itemCount: String = "",
    val isDone: Boolean = false,
    val err: String? = null,
    val loading: Boolean = false
)


data class GroceryItem(
    val id: Int, val name: String,
    @SerializedName("item_count")
    val itemCount: Int)
