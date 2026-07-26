package com.example.newsapp.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.home.components.EmptyView
import com.example.newsapp.presentation.home.components.NewsCard

@Composable
fun BookmarksScreen(
    onArticleClick: (Article) -> Unit,
    viewModel: BookmarksViewModel = hiltViewModel()
) {

    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()

    Scaffold(

    ) { padding ->

        if (bookmarks.isEmpty()) {

            EmptyView(
                modifier = Modifier.padding(padding),
                icon = Icons.Default.Bookmarks,
                title = stringResource(R.string.no_bookmarks),
                subtitle = stringResource(R.string.bookmark_articles_hint)
            )

        } else {

            BookmarksList(
                bookmarks = bookmarks,
                padding = padding,
                onArticleClick = onArticleClick
            )

        }
    }
}
@Composable
private fun BookmarksList(
    bookmarks: List<Article>,
    padding: PaddingValues,
    onArticleClick: (Article) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(bookmarks) { article ->

            NewsCard(
                article = article,
                onClick = {
                    onArticleClick(article)
                }
            )

        }

    }

}