package com.example.viewmodelexample

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.columnSeries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class DetailsScreenViewModel(private val api: GroceriesAPI, savedStateHandle: SavedStateHandle) :
    ViewModel() {

    companion object {
        fun createFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GroceriesApp
                val savedState = createSavedStateHandle()
                DetailsScreenViewModel(app.groceriesAPI, savedState)

            }
        }
    }

    private val _detailsScreenState = MutableStateFlow(DetailsScreenState())


    val currentDateState = MutableStateFlow(LocalDate.now())
    val detailsScreenState = _detailsScreenState.asStateFlow()
    val itemName = savedStateHandle.getStateFlow("itemName", "")

    val modelProducer = CartesianChartModelProducer()

    init {
        getDetails()
    }

    private fun updateChart(dataPoints: List<DetailDataPoint>) {
        viewModelScope.launch {
            modelProducer.runTransaction {

                columnSeries {
                    val days = FloatArray(30)
                    dataPoints.forEachIndexed { index, point ->
                        days[index] = point.value
                    }
                    series(days.toList())
                }
            }
        }
    }

    fun getDetails() {
        viewModelScope.launch {
            try {
                _detailsScreenState.update { currentState -> currentState.copy(loading = true) }
                val dataPoints = api.getDetails(itemName.value)

                updateChart(dataPoints)
            } catch (e: Exception) {
                _detailsScreenState.update { currentState -> currentState.copy(err = e.message) }
            } finally {
                _detailsScreenState.update { currentState -> currentState.copy(loading = false) }
            }
        }
    }


}