package com.segunfrancis.darumandroidassessment.util

object AppConstants {
    const val BASE_URL: String = "https://api.themoviedb.org/3/"
    const val CONNECTIVITY_TIMEOUT: Long = 30L
    const val PAGE_SIZE: Int = 20
    private const val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/"
    private const val POSTER_SIZE: String = "w500/"
    fun buildImageUrl(path: String?): String {
        return IMAGE_BASE_URL.plus(POSTER_SIZE).plus(path)
    }
}
