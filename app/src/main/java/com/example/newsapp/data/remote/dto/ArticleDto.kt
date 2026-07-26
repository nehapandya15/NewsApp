package com.example.newsapp.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleDto(

    val source: SourceDto,

    val author: String?,

    val title: String,

    val description: String?,

    val url: String,

    @Json(name = "urlToImage")
    val imageUrl: String?,

    val publishedAt: String,

    val content: String?
)