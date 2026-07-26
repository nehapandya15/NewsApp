package com.example.newsapp.data.mapper

import com.example.newsapp.data.local.entity.BookmarkEntity
import com.example.newsapp.domain.model.Article

fun BookmarkEntity.toDomain() = Article(
    source = source,
    author = author,
    title = title,
    description = description,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    content = content
)

fun Article.toEntity() = BookmarkEntity(
    source = source,
    author = author,
    title = title,
    description = description,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    content = content
)