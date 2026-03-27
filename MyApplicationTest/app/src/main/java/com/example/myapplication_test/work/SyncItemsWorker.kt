package com.example.myapplication_test.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myapplication_test.constants.AppConstants.Worker
import com.example.myapplication_test.domain.repository.ItemRepository
import kotlinx.coroutines.flow.first

// Runs in the background to sync items even when the app isn't open
// If it fails, it'll retry a few times before giving up
class SyncItemsWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: ItemRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val items = repository.getItems().first()
            Log.d(Worker.TAG, "Synced ${items.size} items successfully")
            Result.success()
        } catch (e: Exception) {
            Log.e(Worker.TAG, "Sync failed: ${e.message}", e)
            if (runAttemptCount < Worker.MAX_RETRIES) Result.retry() else Result.failure()
        }
    }
}
