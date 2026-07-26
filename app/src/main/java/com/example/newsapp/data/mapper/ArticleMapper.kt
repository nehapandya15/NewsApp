package com.example.newsapp.data.mapper

import com.example.newsapp.data.remote.dto.ArticleDto
import com.example.newsapp.domain.model.Article

fun ArticleDto.toDomain() = Article(
    source = source.name,
    author = author,
    title = title,
    description = description,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    content = content
)