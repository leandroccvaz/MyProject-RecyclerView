package com.example.myapplication_test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication_test.constants.AppConstants.ErrorMessages
import com.example.myapplication_test.domain.usecase.GetItemsUseCase
import com.example.myapplication_test.presentation.state.ItemListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

// Talks to the use case, never directly to the API — makes it easy to test with a fake
// Uses StateFlow so the UI just observes changes and reacts automatically
class ItemListViewModel(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ItemListUiState>(ItemListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            getItemsUseCase()
                .onStart { _uiState.value = ItemListUiState.Loading }
                .catch { error ->
                    _uiState.value = ItemListUiState.Error(
                        error.message ?: ErrorMessages.UNKNOWN_ERROR
                    )
                }
                .collect { items ->
                    _uiState.value = ItemListUiState.Success(items)
                }
        }
    }
}
