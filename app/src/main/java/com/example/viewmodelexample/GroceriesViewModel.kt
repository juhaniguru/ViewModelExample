package com.example.viewmodelexample

import android.util.Log
import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GroceriesViewModel(private val api: GroceriesAPI) : ViewModel() {

    companion object {
        fun createFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GroceriesApp
                Log.d(
                    "juhanitestaa",
                    ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY.toString()
                )
                GroceriesViewModel(app.groceriesAPI)
            }
        }
    }

    private val _state = MutableStateFlow(GroceriesState())
    val state = _state.asStateFlow()

    private val _addGroceriesState = MutableStateFlow(AddGroceriesState())
    val addGroceriesState = _addGroceriesState.asStateFlow()


    init {
        getGroceries()
    }

    fun updateName(newName: String) {
        _addGroceriesState.update { currentState -> currentState.copy(name = newName) }
    }

    fun updateItemCount(itemCount: String) {

        _addGroceriesState.update { currentState -> currentState.copy(itemCount = itemCount) }

    }

    fun createGroceries() {
        viewModelScope.launch {
            try {
                val newItem =
                    api.createGroceries(
                        CreateGroceriesReqDto(
                            name = addGroceriesState.value.name,
                            itemCount = addGroceriesState.value.itemCount.toInt()
                        )
                    )
                _state.update { currentState -> currentState.copy(groceries = state.value.groceries + newItem) }
                _addGroceriesState.update { currentState ->
                    currentState.copy(
                        name = "",
                        itemCount = "",
                        isDone = true
                    )
                }
            } catch (e: Exception) {
            } finally {

            }
        }
    }


    fun setIsDone(newIsDone: Boolean) {
        _addGroceriesState.update { currentState -> currentState.copy(isDone = newIsDone) }
    }


    fun getGroceries() {

        viewModelScope.launch {
            try {
                _state.update { currentState -> currentState.copy(loading = true) }

                val groceries = api.getGroceries()
                _state.update { currentState ->
                    currentState.copy(
                        groceries = groceries
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