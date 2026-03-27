package com.example.myapplication_test.domain.model

// This is our "clean" model — it doesn't know anything about JSON or APIs, just plain data
data class Item(
    val id: Int,
    val title: String,
    val description: String
)
