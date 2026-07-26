package com.example.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.core.constants.AppConstants
import com.example.newsapp.data.local.dao.BookmarkDao
import com.example.newsapp.data.mapper.toDomain
import com.example.newsapp.data.mapper.toEntity
import com.example.newsapp.data.remote.api.NewsApi
import com.example.newsapp.data.remote.paging.NewsPagingSource
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val bookmarkDao: BookmarkDao
) : NewsRepository {

    override fun getTopHeadlines(): Flow<PagingData<Article>> {

        return Pager(
            config = PagingConfig(
                pageSize = AppConstants.PAGE_SIZE,
                initialLoadSize = AppConstants.PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                NewsPagingSource(newsApi)
            }
        ).flow
    }

    override fun searchNews(
        query: String
    ): Flow<PagingData<Article>> {

        return Pager(
            config = PagingConfig(pageSize = 20)
        ) {
            NewsPagingSource(
                newsApi,
                query = query
            )
        }.flow
    }

    override fun getBookmarks(): Flow<List<Article>> {
        return bookmarkDao.getBookmarks()
            .map { bookmarks ->
                bookmarks.map { it.toDomain() }
            }
    }

    override suspend fun bookmarkArticle(article: Article) {
        bookmarkDao.insert(article.toEntity())
    }

    override suspend fun removeBookmark(article: Article) {
        bookmarkDao.delete(article.toEntity())
    }

    override fun isBookmarked(url: String): Flow<Boolean> {
        return bookmarkDao.isBookmarked(url)
    }
}