package com.sdm.ecomileage.model.search.response

data class SearchFeedResponse(
    val code: Int,
    val message: String,
    val page: Int,
    val result: Result
)