package com.example.myapplication_test.data.remote

import com.example.myapplication_test.constants.AppConstants.Network
import com.example.myapplication_test.data.model.ItemListResponse
import retrofit2.Response
import retrofit2.http.GET

// Just describes what the API looks like — Retrofit generates the actual HTTP calls
// fun interface so we can mock it with a simple lambda in tests
fun interface ItemApiService {
    @GET(Network.ITEMS_ENDPOINT)
    suspend fun fetchItems(): Response<ItemListResponse>
}
