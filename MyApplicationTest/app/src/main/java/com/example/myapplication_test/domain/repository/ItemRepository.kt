package com.example.myapplication_test.domain.repository

import com.example.myapplication_test.domain.model.Item
import kotlinx.coroutines.flow.Flow

// We define what we need here, but the actual work happens in the data layer
// This way we can swap implementations (real API, fake, Room cache) without touching anything else
interface ItemRepository {
    fun getItems(): Flow<List<Item>>
}
