package com.example.viewmodelexample

data class GroceriesState(
    val loading: Boolean = false,
    val error: String? = null,
    val groceries: List<GroceryItem> = emptyList()
)


data class GroceryItem(val id: Int, val name: String, val itemCount: Int)
