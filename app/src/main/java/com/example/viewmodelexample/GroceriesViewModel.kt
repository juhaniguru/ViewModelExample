package com.example.viewmodelexample

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GroceriesViewModel : ViewModel() {

    val _state = MutableStateFlow(GroceriesState())
    val state = _state.asStateFlow()

    init {
        getGroceries()
    }

    fun getGroceries() {

        viewModelScope.launch {
            try {
                _state.update { currentState -> currentState.copy(loading = true) }
                delay(3000)
                _state.update { currentState ->
                    currentState.copy(
                        groceries = listOf(
                            GroceryItem(
                                id = 1,
                                name = "Maito",
                                itemCount = 2
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                _state.update { currentState -> currentState.copy(error = e.message) }
            } finally {
                _state.update { currentState -> currentState.copy(loading = false) }
            }


        }


    }

}