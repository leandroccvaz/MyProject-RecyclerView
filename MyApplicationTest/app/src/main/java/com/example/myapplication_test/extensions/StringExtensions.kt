package com.example.myapplication_test.extensions

import java.util.Locale

// Handy String shortcuts we can use anywhere — just call "hello".capitalizeFirst()
fun String.capitalizeFirst(locale: Locale = Locale.getDefault()): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }

fun String.truncate(maxLength: Int, suffix: String = "..."): String =
    if (length > maxLength) take(maxLength - suffix.length) + suffix else this
