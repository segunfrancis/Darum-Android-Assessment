package com.segunfrancis.darumandroidassessment.data

import com.segunfrancis.darumandroidassessment.BuildConfig
import com.segunfrancis.darumandroidassessment.data.dto.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): BaseResponse
}
