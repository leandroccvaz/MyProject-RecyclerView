package com.example.myapplication_test.data.mapper

import com.example.myapplication_test.data.model.ItemDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ItemMapperTest {

    @Test
    fun `GIVEN ItemDto WHEN toDomain is called THEN all fields are mapped correctly`() {
        val dto = ItemDto(id = 42, title = "Kotlin", description = "Modern language")

        val domain = dto.toDomain()

        assertEquals(42, domain.id)
        assertEquals("Kotlin", domain.title)
        assertEquals("Modern language", domain.description)
    }

    @Test
    fun `GIVEN list of ItemDto WHEN toDomainList is called THEN all items are mapped`() {
        val dtos = listOf(
            ItemDto(id = 1, title = "T1", description = "D1"),
            ItemDto(id = 2, title = "T2", description = "D2"),
            ItemDto(id = 3, title = "T3", description = "D3")
        )

        val domainList = dtos.toDomainList()

        assertEquals(3, domainList.size)
        assertEquals(1, domainList[0].id)
        assertEquals("T2", domainList[1].title)
        assertEquals("D3", domainList[2].description)
    }

    @Test
    fun `GIVEN empty list WHEN toDomainList is called THEN empty list is returned`() {
        val dtos = emptyList<ItemDto>()

        val domainList = dtos.toDomainList()

        assertTrue(domainList.isEmpty())
    }

    @Test
    fun `GIVEN ItemDto with empty strings WHEN toDomain is called THEN fields are empty strings`() {
        val dto = ItemDto(id = 0, title = "", description = "")

        val domain = dto.toDomain()

        assertEquals(0, domain.id)
        assertEquals("", domain.title)
        assertEquals("", domain.description)
    }
}
