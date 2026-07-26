package com.example.newsapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.home.components.EmptyView
import com.example.newsapp.presentation.home.components.ErrorView
import com.example.newsapp.presentation.home.components.LoadingView
import com.example.newsapp.presentation.home.components.NewsCard
import com.example.newsapp.presentation.home.components.NewsSearchBar
import com.example.newsapp.presentation.home.components.PagingErrorItem
import com.example.newsapp.presentation.home.components.PagingLoadingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onArticleClick: (Article) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val headlines = viewModel.articles.collectAsLazyPagingItems()
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        NewsSearchBar(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                viewModel.onSearchQueryChanged(it)
            }
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {

            if (searchQuery.isBlank()) {
                when (val refreshState = headlines.loadState.refresh) {

                    is LoadState.Loading -> {

                        LoadingView(
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    is LoadState.Error -> {

                        ErrorView(
                            title = stringResource(R.string.failed_to_load_news),
                            message = refreshState.error.localizedMessage
                                ?: stringResource(R.string.unknown_error),
                            onRetry = headlines::retry
                        )
                    }

                    is LoadState.NotLoading -> {

                        if (headlines.itemCount == 0) {

                            EmptyView(
                                title = stringResource(R.string.no_articles),
                                subtitle = stringResource(R.string.pull_to_refresh)
                            )

                        } else {

                            NewsList(
                                articles = headlines,
                                onArticleClick = onArticleClick
                            )

                        }

                    }
                }
            } else {
                when (searchResults.loadState.refresh) {

                    is LoadState.Loading -> {
                        LoadingView()
                    }

                    is LoadState.Error -> {
                        ErrorView(
                            title = stringResource(R.string.search_failed),
                            message = stringResource(R.string.failed_to_search_articles),
                            onRetry = searchResults::retry
                        )
                    }

                    is LoadState.NotLoading -> {

                        if (searchResults.itemCount == 0) {
                            EmptyView(
                                icon = Icons.Default.SearchOff,
                                title = stringResource(R.string.no_search_results),
                                subtitle = stringResource(R.string.try_different_keywords)
                            )
                        } else {
                            NewsList(
                                articles = searchResults,
                                onArticleClick = onArticleClick
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun NewsList(
    articles: LazyPagingItems<Article>,
    onArticleClick: (Article) -> Unit = {}
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(
            count = articles.itemCount
        ) { index ->

            articles[index]?.let {

                NewsCard(
                    article = it,
                    onClick = { onArticleClick(it) }
                )

            }

        }

        item {

            when (articles.loadState.append) {

                is LoadState.Loading -> {
                    PagingLoadingItem()
                }

                is LoadState.Error -> {
                    PagingErrorItem(
                        onRetry = articles::retry
                    )
                }

                else -> Unit
            }
        }

    }

}