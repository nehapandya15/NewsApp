package com.example.newsapp.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.details.components.ArticleWebView
import com.example.newsapp.presentation.details.components.DetailActionBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    article: Article,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.setArticle(article)
    }
    val isBookmarked by viewModel
        .isBookmarked(article.url)
        .collectAsState(initial = false)
    val articleState by viewModel.article.collectAsState()

    articleState?.let { news ->

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            ArticleWebView(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                url = article.url
            )

            DetailActionBar(
                article = article,
                isBookmarked = isBookmarked,
                onBookmarkClick = {
                    viewModel.toggleBookmark(
                        article = article,
                        bookmarked = isBookmarked
                    )
                }
            )
        }

    }

}