package com.example.newsapp.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.usecase.BookmarkArticleUseCase
import com.example.newsapp.domain.usecase.GetBookmarksUseCase
import com.example.newsapp.domain.usecase.RemoveBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase
) : ViewModel() {

    val bookmarks = getBookmarksUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun removeBookmark(article: Article) {
        viewModelScope.launch {
            removeBookmarkUseCase(article)
        }
    }

    fun addBookmark(article: Article) {
        viewModelScope.launch {
            bookmarkArticleUseCase(article)
        }
    }
}