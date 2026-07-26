package com.example.newsapp.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponseDto(

    val status: String,

    val totalResults: Int,

    val articles: List<ArticleDto>

)