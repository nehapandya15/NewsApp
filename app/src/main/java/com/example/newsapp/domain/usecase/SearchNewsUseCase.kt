package com.example.newsapp.domain.usecase

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    operator fun invoke(query: String): Flow<PagingData<Article>> {
        return repository.searchNews(query)
    }
}