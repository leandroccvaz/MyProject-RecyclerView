package com.example.myapplication_test.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.myapplication_test.constants.AppConstants.Worker
import com.example.myapplication_test.domain.model.Item
import com.example.myapplication_test.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SyncItemsWorkerTest {

    private val context: Context = mockk(relaxed = true)
    private val params: WorkerParameters = mockk(relaxed = true)
    private val repository: ItemRepository = mockk()

    @Before
    fun setup() {
        every { params.runAttemptCount } returns 0
    }

    @Test
    fun `GIVEN repository returns items WHEN doWork THEN result is success`() = runTest {
        val items = listOf(Item(1, "T", "D"))
        every { repository.getItems() } returns flowOf(items)

        val worker = SyncItemsWorker(context, params, repository)
        val result = worker.doWork()

        assertEquals(ListenableWorker.Result.success(), result)
    }

    @Test
    fun `GIVEN repository throws and attempts below max WHEN doWork THEN result is retry`() = runTest {
        every { repository.getItems() } returns flow { throw RuntimeException("Network error") }
        every { params.runAttemptCount } returns 1

        val worker = SyncItemsWorker(context, params, repository)
        val result = worker.doWork()

        assertEquals(ListenableWorker.Result.retry(), result)
    }

    @Test
    fun `GIVEN repository throws and attempts at max WHEN doWork THEN result is failure`() = runTest {
        every { repository.getItems() } returns flow { throw RuntimeException("Network error") }
        every { params.runAttemptCount } returns Worker.MAX_RETRIES

        val worker = SyncItemsWorker(context, params, repository)
        val result = worker.doWork()

        assertEquals(ListenableWorker.Result.failure(), result)
    }

    @Test
    fun `GIVEN repository returns empty list WHEN doWork THEN result is still success`() = runTest {
        every { repository.getItems() } returns flowOf(emptyList())

        val worker = SyncItemsWorker(context, params, repository)
        val result = worker.doWork()

        assertEquals(ListenableWorker.Result.success(), result)
    }
}
