package com.example.viewmodelexample

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


class GroceriesAPIMock(
    private val data: List<GroceryItem> = emptyList(),
    private val err: String? = null
) : GroceriesAPI {
    override suspend fun getGroceries(): List<GroceryItem> {
        if(err != null) {
            throw Exception(err)
        }

        return data
    }

    override suspend fun createGroceries(reqData: CreateGroceriesReqDto): GroceryItem {
        TODO("Not yet implemented")
    }

    override suspend fun getDetails(name: String): List<DetailDataPoint> {
        TODO("Not yet implemented")
    }
}

class GroceriesViewModelTests {
    private lateinit var vm: GroceriesViewModel
    private lateinit var mockAPI: GroceriesAPI

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {

        Dispatchers.setMain(Dispatchers.Unconfined)

    }

    @Test
    fun testGetGroceriesError(): Unit = runTest {
        // Arrange
        mockAPI = GroceriesAPIMock(err = "Tämä on keinotekoinen virhe")
        vm = GroceriesViewModel(mockAPI)

        // Act
        vm.getGroceries()

        // Assert
        assertNotNull(vm.state.value.error)




    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}