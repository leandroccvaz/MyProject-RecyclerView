package com.example.myapplication_test.di

import com.example.myapplication_test.constants.AppConstants.Network
import com.example.myapplication_test.data.remote.ItemApiService
import com.example.myapplication_test.data.remote.MockInterceptor
import com.example.myapplication_test.data.repository.ItemRepositoryImpl
import com.example.myapplication_test.domain.repository.ItemRepository
import com.example.myapplication_test.domain.usecase.GetItemsUseCase
import com.example.myapplication_test.presentation.viewmodel.ItemListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// All our dependency wiring lives here — split into small modules so tests can swap pieces out

// Sets up the HTTP stack: OkHttp client, mock interceptor, and Retrofit
val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single { MockInterceptor() }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<MockInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Network.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ItemApiService> {
        get<Retrofit>().create(ItemApiService::class.java)
    }
}

// Connects the repository interface to its real implementation
val repositoryModule = module {
    single<ItemRepository> { ItemRepositoryImpl(apiService = get()) }
}

// Fresh use case every time it's needed — no shared state to worry about
val useCaseModule = module {
    factory { GetItemsUseCase(repository = get()) }
}

// ViewModel survives screen rotations and gets cleaned up when the screen goes away
val viewModelModule = module {
    viewModel { ItemListViewModel(getItemsUseCase = get()) }
}

val appModules = listOf(
    networkModule,
    repositoryModule,
    useCaseModule,
    viewModelModule,
    workModule
)
