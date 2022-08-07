package com.segunfrancis.darumandroidassessment.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.segunfrancis.darumandroidassessment.data.PopularMoviesPagingSource
import com.segunfrancis.darumandroidassessment.data.dto.MovieResponse
import com.segunfrancis.darumandroidassessment.util.AppConstants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class ListViewModel @Inject constructor(private val source: PopularMoviesPagingSource) :
    ViewModel() {

    private var currentPopularMoviesResult: Flow<PagingData<MovieResponse>>? = null

    fun getPopularMovies(): Flow<PagingData<MovieResponse>> {
        val lastPopularMoviesResult = currentPopularMoviesResult
        return lastPopularMoviesResult
            ?: Pager(
                PagingConfig(pageSize = PAGE_SIZE)
            ) {
                source
            }.flow
                .cachedIn(viewModelScope)
    }
}
