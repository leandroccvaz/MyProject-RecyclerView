package com.example.myapplication_test.data.repository

import app.cash.turbine.test
import com.example.myapplication_test.data.model.ItemDto
import com.example.myapplication_test.data.model.ItemListResponse
import com.example.myapplication_test.data.remote.ItemApiService
import com.example.myapplication_test.testutils.TestDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class ItemRepositoryImplTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private val apiService: ItemApiService = mockk()
    private val repository = ItemRepositoryImpl(apiService)

    @Test
    fun `GIVEN api returns success WHEN getItems is collected THEN domain items are emitted`() = runTest {
        val dtos = listOf(
            ItemDto(id = 1, title = "T1", description = "D1"),
            ItemDto(id = 2, title = "T2", description = "D2")
        )
        coEvery { apiService.fetchItems() } returns Response.success(ItemListResponse(dtos))

        repository.getItems().test {
            val items = awaitItem()
            assertEquals(2, items.size)
            assertEquals("T1", items[0].title)
            assertEquals("D2", items[1].description)
            assertEquals(1, items[0].id)
            awaitComplete()
        }

        coVerify(exactly = 1) { apiService.fetchItems() }
    }

    @Test
    fun `GIVEN api returns null body WHEN getItems is collected THEN empty list is emitted`() = runTest {
        coEvery { apiService.fetchItems() } returns Response.success(null)

        repository.getItems().test {
            val items = awaitItem()
            assertTrue(items.isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `GIVEN api returns error WHEN getItems is collected THEN RuntimeException is thrown`() = runTest {
        coEvery { apiService.fetchItems() } returns Response.error(
            500,
            "Internal Server Error".toResponseBody()
        )

        repository.getItems().test {
            val error = awaitError()
            assertTrue(error is RuntimeException)
            assertTrue(error.message!!.contains("500"))
        }
    }

    @Test
    fun `GIVEN api returns success with empty items WHEN getItems is collected THEN empty list is emitted`() = runTest {
        coEvery { apiService.fetchItems() } returns Response.success(ItemListResponse(emptyList()))

        repository.getItems().test {
            val items = awaitItem()
            assertTrue(items.isEmpty())
            awaitComplete()
        }
    }
}
