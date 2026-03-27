package com.example.myapplication_test.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication_test.constants.AppConstants.TestTags
import com.example.myapplication_test.constants.AppConstants.Ui
import com.example.myapplication_test.domain.model.Item
import com.example.myapplication_test.presentation.state.ItemListUiState
import com.example.myapplication_test.presentation.viewmodel.ItemListViewModel
import org.koin.androidx.compose.koinViewModel

// The screen that the user actually sees — grabs the ViewModel from Koin and wires everything up
// ItemListContent is pulled out separately so we can test it without needing the full DI setup
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    viewModel: ItemListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Ui.TOOLBAR_TITLE) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(
                        onClick = { viewModel.loadItems() },
                        modifier = Modifier.testTag(TestTags.REFRESH_BUTTON)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = Ui.REFRESH_DESCRIPTION
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        ItemListContent(
            uiState = uiState,
            paddingValues = paddingValues,
            onItemClick = { item ->
                Toast.makeText(context, "${Ui.CLICK_TOAST_PREFIX} ${item.title}", Toast.LENGTH_SHORT).show()
            },
            onRetryClick = { viewModel.loadItems() }
        )
    }
}

@Composable
fun ItemListContent(
    uiState: ItemListUiState,
    paddingValues: PaddingValues,
    onItemClick: (Item) -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is ItemListUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.testTag(TestTags.LOADING_INDICATOR)
                )
            }

            is ItemListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(TestTags.ITEMS_LIST),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(
                        items = uiState.items,
                        key = { it.id }
                    ) { item ->
                        ItemCard(
                            item = item,
                            onItemClick = onItemClick
                        )
                    }
                }
            }

            is ItemListUiState.Error -> {
                ErrorContent(
                    message = uiState.message,
                    onRetryClick = onRetryClick
                )
            }
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TestTags.ERROR_CONTENT),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemListContentSuccessPreview() {
    val items = listOf(
        Item(1, "Kotlin", "Modern language"),
        Item(2, "Compose", "Declarative UI")
    )
    ItemListContent(
        uiState = ItemListUiState.Success(items),
        paddingValues = PaddingValues(0.dp),
        onItemClick = {},
        onRetryClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ItemListContentLoadingPreview() {
    ItemListContent(
        uiState = ItemListUiState.Loading,
        paddingValues = PaddingValues(0.dp),
        onItemClick = {},
        onRetryClick = {}
    )
}
