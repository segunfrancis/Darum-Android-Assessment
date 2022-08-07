package com.segunfrancis.darumandroidassessment.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.segunfrancis.darumandroidassessment.data.dto.MovieResponse
import javax.inject.Inject

class PopularMoviesPagingSource @Inject constructor(private val api: MoviesService) :
    PagingSource<Int, MovieResponse>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = api.getPopularMovies(page = position)
            val movies = response.results
            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}
