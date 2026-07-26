package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getTopHeadlines(): Flow<PagingData<Article>>
    fun searchNews(
        query: String
    ): Flow<PagingData<Article>>
    fun getBookmarks(): Flow<List<Article>>

    suspend fun bookmarkArticle(article: Article)

    suspend fun removeBookmark(article: Article)

    fun isBookmarked(url: String): Flow<Boolean>
}