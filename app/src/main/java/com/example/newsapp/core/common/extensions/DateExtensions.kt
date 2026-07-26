package com.example.newsapp.core.common.extensions

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private const val INPUT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val OUTPUT_PATTERN = "dd MMM yyyy • hh:mm a"
fun String.toFormattedDate(): String {
    return try {
        val inputFormat = SimpleDateFormat(INPUT_PATTERN, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val outputFormat = SimpleDateFormat(OUTPUT_PATTERN, Locale.getDefault())
        val date = inputFormat.parse(this)
        date?.let { outputFormat.format(it) } ?: this
    } catch (e: Exception) {
        this
    }

}