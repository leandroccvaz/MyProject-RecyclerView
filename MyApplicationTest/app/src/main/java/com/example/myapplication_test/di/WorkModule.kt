package com.example.myapplication_test.di

import com.example.myapplication_test.work.SyncItemsWorker
import com.example.myapplication_test.work.WorkManagerScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import androidx.work.WorkManager

// Tells Koin how to build our background workers with all the dependencies they need
val workModule = module {
    single { WorkManager.getInstance(androidContext()) }
    single { WorkManagerScheduler(workManager = get()) }
    worker { SyncItemsWorker(get(), get(), repository = get()) }
}
