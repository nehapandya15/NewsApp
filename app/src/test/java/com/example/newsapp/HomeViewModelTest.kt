package com.example.newsapp

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.SearchNewsUseCase
import com.example.newsapp.presentation.home.HomeViewModel
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class HomeViewModelTest {


    @Test
    fun headlines_areExposed() {

        // Arrange
        val getTopHeadlinesUseCase =
            mock(GetTopHeadlinesUseCase::class.java)

        val searchNewsUseCase =
            mock(SearchNewsUseCase::class.java)


        `when`(
            getTopHeadlinesUseCase()
        ).thenReturn(
            flowOf(PagingData.empty<Article>())
        )


        val viewModel = HomeViewModel(
            getTopHeadlinesUseCase,
            searchNewsUseCase
        )


        // Assert
        assertNotNull(viewModel.articles)
    }


    @Test
    fun searchQuery_updates() {

        // Arrange
        val getTopHeadlinesUseCase =
            mock(GetTopHeadlinesUseCase::class.java)

        val searchNewsUseCase =
            mock(SearchNewsUseCase::class.java)


        `when`(
            getTopHeadlinesUseCase()
        ).thenReturn(
            flowOf(PagingData.empty<Article>())
        )


        `when`(
            searchNewsUseCase("android")
        ).thenReturn(
            flowOf(PagingData.empty<Article>())
        )


        val viewModel = HomeViewModel(
            getTopHeadlinesUseCase,
            searchNewsUseCase
        )


        // Act
        viewModel.onSearchQueryChanged("android")


        // Assert
        assertNotNull(viewModel.searchResults)
    }
}