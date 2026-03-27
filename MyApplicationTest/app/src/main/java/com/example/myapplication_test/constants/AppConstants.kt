package com.example.myapplication_test.constants

// Centralized constants — change a value here and it updates everywhere
object AppConstants {

    object ErrorMessages {
        const val UNKNOWN_ERROR = "Unknown error occurred"
        const val API_ERROR_PREFIX = "API error:"
    }

    object Network {
        const val BASE_URL = "https://mock.api.example.com/"
        const val ITEMS_ENDPOINT = "items"
        const val CONTENT_TYPE_JSON = "application/json"
        const val RESPONSE_OK = "OK"
        const val NOT_FOUND_JSON = """{"error": "not found"}"""
    }

    object TestTags {
        const val REFRESH_BUTTON = "refresh_button"
        const val LOADING_INDICATOR = "loading_indicator"
        const val ITEMS_LIST = "items_list"
        const val ERROR_CONTENT = "error_content"
        const val ITEM_CARD_PREFIX = "item_card_"
    }

    object Ui {
        const val TOOLBAR_TITLE = "MyCustomRecyclerView"
        const val REFRESH_DESCRIPTION = "Refresh"
        const val CLICK_TOAST_PREFIX = "You've Clicked:"
    }

    object Worker {
        const val TAG = "SyncItemsWorker"
        const val WORK_NAME = "sync_items_work"
        const val MAX_RETRIES = 3
        const val REPEAT_INTERVAL_HOURS = 6L
        const val BACKOFF_DELAY_MINUTES = 10L
    }
}
