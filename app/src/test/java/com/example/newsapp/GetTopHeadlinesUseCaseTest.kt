package com.example.newsapp

import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertSame
import org.junit.Test

class GetTopHeadlinesUseCaseTest {

    @Test
    fun invoke_returnsRepositoryFlow() {
        val expectedFlow = flowOf(PagingData.empty<Article>())
        val repository = FakeNewsRepository(expectedFlow)
        val useCase = GetTopHeadlinesUseCase(repository)

        val actualFlow = useCase()

        assertSame(expectedFlow, actualFlow)
    }
}

private class FakeNewsRepository(
    private val headlinesFlow: Flow<PagingData<Article>>
) : NewsRepository {

    override fun getTopHeadlines(): Flow<PagingData<Article>> = headlinesFlow

    override fun searchNews(query: String): Flow<PagingData<Article>> =
        flowOf(PagingData.empty())

    override fun getBookmarks() = flowOf(emptyList<Article>())

    override suspend fun bookmarkArticle(article: Article) {}

    override suspend fun removeBookmark(article: Article) {}

    override fun isBookmarked(url: String) = flowOf(false)
}