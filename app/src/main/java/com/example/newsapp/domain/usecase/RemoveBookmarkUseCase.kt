package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(article: Article) {
        repository.removeBookmark(article)
    }
}