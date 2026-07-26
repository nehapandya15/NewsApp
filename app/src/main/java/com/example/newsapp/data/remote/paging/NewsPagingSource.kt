package com.example.newsapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.core.constants.AppConstants
import com.example.newsapp.data.mapper.toDomain
import com.example.newsapp.data.remote.api.NewsApi
import com.example.newsapp.domain.model.Article
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val newsApi: NewsApi,
    private val query: String? = null
) : PagingSource<Int, Article>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Article> {

        return try {

            val currentPage = params.key ?: 1
            val response = if (query.isNullOrBlank()) {
                newsApi.getTopHeadlines(
                    country = AppConstants.COUNTRY,
                    page = currentPage,
                    pageSize = AppConstants.PAGE_SIZE
                )
            } else {
                newsApi.searchNews(
                    query = query,
                    page = currentPage,
                    pageSize = AppConstants.PAGE_SIZE
                )
            }
            val articles = response.articles.map { articleDto ->
                articleDto.toDomain()
            }

            LoadResult.Page(
                data = articles,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (articles.isEmpty()) null else currentPage + 1
            )

        } catch (exception: IOException) {

            LoadResult.Error(exception)

        } catch (exception: HttpException) {

            LoadResult.Error(exception)

        } catch (exception: Exception) {

            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, Article>
    ): Int? {

        return state.anchorPosition?.let { anchorPosition ->

            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }
}