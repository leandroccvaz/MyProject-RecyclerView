package com.example.myapplication_test.work

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.myapplication_test.constants.AppConstants.Worker
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class WorkManagerSchedulerTest {

    private val workManager: WorkManager = mockk(relaxed = true)
    private val scheduler = WorkManagerScheduler(workManager)

    @Test
    fun `WHEN schedulePeriodicSync is called THEN enqueueUniquePeriodicWork is invoked with correct name`() {
        scheduler.schedulePeriodicSync()

        val nameSlot = slot<String>()
        val policySlot = slot<ExistingPeriodicWorkPolicy>()

        verify {
            workManager.enqueueUniquePeriodicWork(
                capture(nameSlot),
                capture(policySlot),
                any<PeriodicWorkRequest>()
            )
        }

        assertEquals(Worker.WORK_NAME, nameSlot.captured)
        assertEquals(ExistingPeriodicWorkPolicy.KEEP, policySlot.captured)
    }

    @Test
    fun `WHEN cancelSync is called THEN cancelUniqueWork is invoked`() {
        scheduler.cancelSync()

        verify(exactly = 1) {
            workManager.cancelUniqueWork(Worker.WORK_NAME)
        }
    }

    @Test
    fun `GIVEN worker constants THEN values are correct`() {
        assertEquals(6L, Worker.REPEAT_INTERVAL_HOURS)
        assertEquals(10L, Worker.BACKOFF_DELAY_MINUTES)
    }
}
