package com.example.myapplication_test.presentation.state

import com.example.myapplication_test.domain.model.Item

// Every possible screen state lives here — if we add a new one, the compiler will remind us to handle it
sealed interface ItemListUiState {
    data object Loading : ItemListUiState
    data class Success(val items: List<Item>) : ItemListUiState
    data class Error(val message: String) : ItemListUiState
}
