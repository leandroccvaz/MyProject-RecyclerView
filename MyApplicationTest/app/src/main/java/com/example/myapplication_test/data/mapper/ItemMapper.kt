package com.example.myapplication_test.data.mapper

import com.example.myapplication_test.data.model.ItemDto
import com.example.myapplication_test.domain.model.Item

// Converts API objects into our clean domain objects
// Extension functions so we can just write dto.toDomain() — reads nicer than a separate Mapper class
fun ItemDto.toDomain(): Item = Item(
    id = id,
    title = title,
    description = description
)

fun List<ItemDto>.toDomainList(): List<Item> = map { it.toDomain() }
