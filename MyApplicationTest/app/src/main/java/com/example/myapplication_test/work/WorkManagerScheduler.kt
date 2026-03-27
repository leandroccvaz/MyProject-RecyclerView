package com.example.myapplication_test.work

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapplication_test.constants.AppConstants.Worker
import java.util.concurrent.TimeUnit

// Handles when and how often the background sync runs — the rest of the app just calls schedulePeriodicSync()
class WorkManagerScheduler(
    private val workManager: WorkManager
) {

    fun schedulePeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncItemsWorker>(
            repeatInterval = Worker.REPEAT_INTERVAL_HOURS,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Worker.BACKOFF_DELAY_MINUTES, TimeUnit.MINUTES)
            .addTag(Worker.TAG)
            .build()

        workManager.enqueueUniquePeriodicWork(
            Worker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    fun cancelSync() {
        workManager.cancelUniqueWork(Worker.WORK_NAME)
    }
}
