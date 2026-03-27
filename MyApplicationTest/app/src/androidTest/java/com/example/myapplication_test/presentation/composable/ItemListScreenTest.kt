package com.example.myapplication_test.presentation.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.example.myapplication_test.domain.model.Item
import com.example.myapplication_test.presentation.state.ItemListUiState
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented Compose UI tests for the items list screen.
 *
 * These run on a device/emulator (androidTest source set) and exercise
 * the actual Compose rendering pipeline — catching layout, accessibility,
 * and interaction bugs that unit tests can't.
 *
 * We test ItemListContent (the stateless composable) directly instead of
 * ItemListScreen, so we don't need Koin or a ViewModel — we just pass
 * the UiState we want to verify. This is why we split the screen into
 * a "smart" composable (ItemListScreen) and a "dumb" one (ItemListContent).
 *
 * testTag modifiers on key nodes enable reliable node lookup without
 * depending on visible text that might change with i18n.
 */
class ItemListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenLoadingState_whenRendered_thenLoadingIndicatorIsDisplayed() {
        composeTestRule.setContent {
            ItemListContent(
                uiState = ItemListUiState.Loading,
                paddingValues = PaddingValues(0.dp),
                onItemClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun givenSuccessState_whenRendered_thenItemsAreDisplayed() {
        val items = listOf(
            Item(id = 1, title = "Kotlin Coroutines", description = "Async programming"),
            Item(id = 2, title = "Jetpack Compose", description = "Declarative UI")
        )

        composeTestRule.setContent {
            ItemListContent(
                uiState = ItemListUiState.Success(items),
                paddingValues = PaddingValues(0.dp),
                onItemClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.onNodeWithText("Kotlin Coroutines").assertIsDisplayed()
        composeTestRule.onNodeWithText("Async programming").assertIsDisplayed()
        composeTestRule.onNodeWithText("Jetpack Compose").assertIsDisplayed()
        composeTestRule.onNodeWithText("Declarative UI").assertIsDisplayed()
        composeTestRule.onNodeWithTag("items_list").assertIsDisplayed()
    }

    @Test
    fun givenErrorState_whenRendered_thenErrorMessageIsDisplayed() {
        composeTestRule.setContent {
            ItemListContent(
                uiState = ItemListUiState.Error("Network failure"),
                paddingValues = PaddingValues(0.dp),
                onItemClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.onNodeWithText("Network failure").assertIsDisplayed()
        composeTestRule.onNodeWithTag("error_content").assertIsDisplayed()
    }

    @Test
    fun givenSuccessState_whenItemIsClicked_thenCallbackIsInvoked() {
        var clickedItem: Item? = null
        val items = listOf(
            Item(id = 1, title = "Click Me", description = "Test item")
        )

        composeTestRule.setContent {
            ItemListContent(
                uiState = ItemListUiState.Success(items),
                paddingValues = PaddingValues(0.dp),
                onItemClick = { clickedItem = it },
                onRetryClick = {}
            )
        }

        composeTestRule.onNodeWithTag("item_card_1").performClick()
        assert(clickedItem?.title == "Click Me")
    }

    @Test
    fun givenEmptySuccessState_whenRendered_thenListIsEmpty() {
        composeTestRule.setContent {
            ItemListContent(
                uiState = ItemListUiState.Success(emptyList()),
                paddingValues = PaddingValues(0.dp),
                onItemClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.onNodeWithTag("items_list").assertIsDisplayed()
    }
}
