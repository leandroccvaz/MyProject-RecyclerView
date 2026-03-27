package com.example.myapplication_test.di

import com.example.myapplication_test.testutils.TestDispatcherRule
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

// workModule excluded — it requires androidContext() which is unavailable in JVM unit tests
private val testableModules = listOf(
    networkModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)

class AppModuleTest : KoinTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Test
    fun `GIVEN app modules WHEN checkModules is called THEN all definitions resolve`() {
        koinApplication {
            modules(testableModules)
        }.checkModules()
    }
}
