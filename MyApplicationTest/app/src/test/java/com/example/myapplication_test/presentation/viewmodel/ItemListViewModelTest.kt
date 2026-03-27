package com.example.myapplication_test.presentation.viewmodel

import app.cash.turbine.test
import com.example.myapplication_test.constants.AppConstants.ErrorMessages
import com.example.myapplication_test.domain.model.Item
import com.example.myapplication_test.domain.usecase.GetItemsUseCase
import com.example.myapplication_test.presentation.state.ItemListUiState
import com.example.myapplication_test.testutils.TestDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for [ItemListViewModel] following the same patterns used in
 * MultiLanguageViewModelTest from membership-android/orchestrator:
 *
 * - TestDispatcherRule replaces Dispatchers.Main with UnconfinedTestDispatcher
 *   so coroutines execute synchronously (same as orchestrator's @Before/setMain)
 * - MockK with every {} stubs the use case's Flow return
 * - Turbine's .test {} collects StateFlow emissions for assertion
 * - GIVEN/WHEN/THEN naming convention for readability
 *
 * createViewModel() is a factory method because the ViewModel calls loadItems()
 * in init {} — we need the mock configured before construction.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ItemListViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private val getItemsUseCase: GetItemsUseCase = mockk()

    private fun createViewModel(): ItemListViewModel {
        return ItemListViewModel(getItemsUseCase)
    }

    @Test
    fun `GIVEN successful response WHEN viewModel is created THEN uiState should be Success with items`() = runTest {
        val expectedItems = listOf(
            Item(id = 1, title = "Title 1", description = "Desc 1"),
            Item(id = 2, title = "Title 2", description = "Desc 2")
        )
        every { getItemsUseCase() } returns flowOf(expectedItems)

        val viewModel = createViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is ItemListUiState.Success)
            assertEquals(expectedItems, (state as ItemListUiState.Success).items)
        }
    }

    @Test
    fun `GIVEN api throws exception WHEN viewModel is created THEN uiState should be Error`() = runTest {
        val errorMessage = "Network failure"
        every { getItemsUseCase() } returns flow { throw RuntimeException(errorMessage) }

        val viewModel = createViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is ItemListUiState.Error)
            assertEquals(errorMessage, (state as ItemListUiState.Error).message)
        }
    }

    @Test
    fun `GIVEN successful response WHEN loadItems is called THEN use case is invoked again`() = runTest {
        val items = listOf(Item(id = 1, title = "T", description = "D"))
        every { getItemsUseCase() } returns flowOf(items)

        val viewModel = createViewModel()
        viewModel.loadItems()

        verify(atLeast = 2) { getItemsUseCase() }
    }

    @Test
    fun `GIVEN empty list WHEN viewModel is created THEN uiState should be Success with empty list`() = runTest {
        every { getItemsUseCase() } returns flowOf(emptyList())

        val viewModel = createViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is ItemListUiState.Success)
            assertTrue((state as ItemListUiState.Success).items.isEmpty())
        }
    }

    @Test
    fun `GIVEN exception with null message WHEN viewModel is created THEN uiState Error shows fallback message`() = runTest {
        every { getItemsUseCase() } returns flow { throw RuntimeException() }

        val viewModel = createViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is ItemListUiState.Error)
            assertEquals(ErrorMessages.UNKNOWN_ERROR, (state as ItemListUiState.Error).message)
        }
    }
}
