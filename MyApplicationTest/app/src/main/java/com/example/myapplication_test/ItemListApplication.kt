package com.example.myapplication_test

import android.app.Application
import androidx.work.Configuration
import com.example.myapplication_test.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

// First thing that runs when the app starts — sets up all our dependency injection
// Also tells WorkManager to let Koin create workers so they get their dependencies
class ItemListApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ItemListApplication)
            workManagerFactory()
            modules(appModules)
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}
