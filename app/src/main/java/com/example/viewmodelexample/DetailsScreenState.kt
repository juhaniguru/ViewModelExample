package com.example.viewmodelexample

data class DetailsScreenState(
    val loading: Boolean = false,
    val err: String? = null,
    val dataPoints: List<DetailDataPoint> = emptyList()
)

data class DetailDataPoint(val name: String, val value: Float)
