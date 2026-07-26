package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsBookmarkedUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    operator fun invoke(url: String): Flow<Boolean> {
        return repository.isBookmarked(url)
    }
}