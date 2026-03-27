package com.example.myapplication_test.data.model

import com.google.gson.annotations.SerializedName

// Mirrors the JSON shape from the API — if a field name changes on the backend, we only fix it here
data class ItemDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)

data class ItemListResponse(
    @SerializedName("items") val items: List<ItemDto>
)
