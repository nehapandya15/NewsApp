package com.example.newsapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.usecase.BookmarkArticleUseCase
import com.example.newsapp.domain.usecase.IsBookmarkedUseCase
import com.example.newsapp.domain.usecase.RemoveBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val isBookmarkedUseCase: IsBookmarkedUseCase
) : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> = _article.asStateFlow()

    fun setArticle(article: Article) {
        _article.value = article
    }

    fun isBookmarked(url: String) =
        isBookmarkedUseCase(url)

    fun toggleBookmark(
        article: Article,
        bookmarked: Boolean
    ) {
        viewModelScope.launch {
            if (bookmarked) {
                removeBookmarkUseCase(article)
            } else {
                bookmarkArticleUseCase(article)
            }
        }
    }
}