package com.segunfrancis.darumandroidassessment.data.dto

data class BaseResponse(
    val page: Int,
    val results: List<MovieResponse>,
    val total_pages: Int,
    val total_results: Int
)
