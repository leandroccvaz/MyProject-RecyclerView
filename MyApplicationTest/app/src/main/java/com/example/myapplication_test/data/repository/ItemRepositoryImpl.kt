package com.example.myapplication_test.data.repository

import com.example.myapplication_test.constants.AppConstants.ErrorMessages
import com.example.myapplication_test.data.mapper.toDomainList
import com.example.myapplication_test.data.remote.ItemApiService
import com.example.myapplication_test.domain.model.Item
import com.example.myapplication_test.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// Does the actual API call, converts the response, and hands back clean domain objects
// The rest of the app never sees Retrofit or DTOs — just a Flow of Items
class ItemRepositoryImpl(
    private val apiService: ItemApiService
) : ItemRepository {

    override fun getItems(): Flow<List<Item>> = flow {
        val response = apiService.fetchItems()
        if (response.isSuccessful) {
            val items = response.body()?.items?.toDomainList() ?: emptyList()
            emit(items)
        } else {
            throw RuntimeException("${ErrorMessages.API_ERROR_PREFIX} ${response.code()} ${response.message()}")
        }
    }
}
