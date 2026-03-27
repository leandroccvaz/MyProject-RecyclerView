package com.example.myapplication_test.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `GIVEN lowercase string WHEN capitalizeFirst is called THEN first char is uppercase`() {
        assertEquals("Hello", "hello".capitalizeFirst())
    }

    @Test
    fun `GIVEN already capitalized WHEN capitalizeFirst is called THEN remains unchanged`() {
        assertEquals("Hello", "Hello".capitalizeFirst())
    }

    @Test
    fun `GIVEN empty string WHEN capitalizeFirst is called THEN empty is returned`() {
        assertEquals("", "".capitalizeFirst())
    }

    @Test
    fun `GIVEN single char WHEN capitalizeFirst is called THEN it is uppercased`() {
        assertEquals("A", "a".capitalizeFirst())
    }

    @Test
    fun `GIVEN long string WHEN truncate is called with shorter max THEN string is truncated with suffix`() {
        assertEquals("Hell...", "Hello World".truncate(7))
    }

    @Test
    fun `GIVEN short string WHEN truncate is called with longer max THEN string remains unchanged`() {
        assertEquals("Hi", "Hi".truncate(10))
    }

    @Test
    fun `GIVEN string WHEN truncate is called with exact length THEN string remains unchanged`() {
        assertEquals("Hello", "Hello".truncate(5))
    }

    @Test
    fun `GIVEN string WHEN truncate with custom suffix THEN custom suffix is used`() {
        assertEquals("He~~", "Hello World".truncate(4, "~~"))
    }
}
