package com.example.myapplication_test.presentation.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.myapplication_test.domain.model.Item
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented tests for the [ItemCard] composable in isolation.
 * Verifies that the card renders title/description and that click
 * callbacks propagate the correct Item back to the caller.
 */
class ItemCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenItem_whenCardIsRendered_thenTitleAndDescriptionAreDisplayed() {
        val item = Item(id = 1, title = "Test Title", description = "Test Description")

        composeTestRule.setContent {
            ItemCard(item = item, onItemClick = {})
        }

        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Description").assertIsDisplayed()
        composeTestRule.onNodeWithTag("item_card_1").assertIsDisplayed()
    }

    @Test
    fun givenItem_whenCardIsClicked_thenCallbackReceivesCorrectItem() {
        var clickedItem: Item? = null
        val item = Item(id = 5, title = "Clickable", description = "Card")

        composeTestRule.setContent {
            ItemCard(item = item, onItemClick = { clickedItem = it })
        }

        composeTestRule.onNodeWithTag("item_card_5").performClick()
        assert(clickedItem == item)
    }
}
