package com.example.myapplication_test.domain.usecase

import com.example.myapplication_test.domain.model.Item
import com.example.myapplication_test.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

// Sits between the ViewModel and the repository — if we ever need to add filtering or sorting, it goes here
// operator invoke() is just syntactic sugar so we can call it like a function: getItemsUseCase()
class GetItemsUseCase(
    private val repository: ItemRepository
) {
    operator fun invoke(): Flow<List<Item>> = repository.getItems()
}
