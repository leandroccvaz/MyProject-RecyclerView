package com.example.myapplication_test.domain.usecase

import app.cash.turbine.test
import com.example.myapplication_test.domain.model.Item
import com.example.myapplication_test.domain.repository.ItemRepository
import com.example.myapplication_test.testutils.TestDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class GetItemsUseCaseTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private val repository: ItemRepository = mockk()
    private val useCase = GetItemsUseCase(repository)

    @Test
    fun `GIVEN repository returns items WHEN invoke is called THEN items are emitted`() = runTest {
        val expected = listOf(
            Item(id = 1, title = "T1", description = "D1"),
            Item(id = 2, title = "T2", description = "D2")
        )
        every { repository.getItems() } returns flowOf(expected)

        useCase().test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }

        verify(exactly = 1) { repository.getItems() }
    }

    @Test
    fun `GIVEN repository returns empty list WHEN invoke is called THEN empty list is emitted`() = runTest {
        every { repository.getItems() } returns flowOf(emptyList())

        useCase().test {
            assertTrue(awaitItem().isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `GIVEN repository throws WHEN invoke is called THEN error is propagated`() = runTest {
        every { repository.getItems() } returns flow { throw RuntimeException("Error") }

        useCase().test {
            val error = awaitError()
            assertEquals("Error", error.message)
        }
    }
}
