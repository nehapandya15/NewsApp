package com.example.newsapp

import com.example.newsapp.data.local.dao.BookmarkDao
import com.example.newsapp.data.mapper.toEntity
import com.example.newsapp.data.remote.api.NewsApi
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class NewsRepositoryImplTest {

    private val newsApi = mock(NewsApi::class.java)
    private val bookmarkDao = mock(BookmarkDao::class.java)

    private val repository = NewsRepositoryImpl(
        newsApi,
        bookmarkDao
    )


    @Test
    fun bookmarkArticle_callsDaoInsert() = runTest {

        val article = createTestArticle()
        repository.bookmarkArticle(article)

        verify(bookmarkDao)
            .insert(article.toEntity())
    }


    @Test
    fun removeBookmark_callsDaoDelete() = runTest {

        val article = createTestArticle()
        repository.removeBookmark(article)

        verify(bookmarkDao)
            .delete(article.toEntity())
    }
    private fun createTestArticle() = Article(
        source = "BBC News",
        author = "John Doe",
        title = "Test Title",
        description = "Test Description",
        url = "https://example.com/news",
        imageUrl = "https://example.com/image.jpg",
        publishedAt = "2025-01-01T10:00:00Z",
        content = "Test Content"
    )
}