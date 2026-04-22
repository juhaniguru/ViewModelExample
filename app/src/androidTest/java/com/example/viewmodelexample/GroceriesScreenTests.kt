package com.example.viewmodelexample

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GroceriesScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testGroceriesScreenLoading() {
        composeTestRule.setContent {
            GroceriesScreen(
                state = GroceriesState(loading = true),
                onNavigate = {},

                onNavigateToDetails = {}
            )
        }

        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()
    }
}