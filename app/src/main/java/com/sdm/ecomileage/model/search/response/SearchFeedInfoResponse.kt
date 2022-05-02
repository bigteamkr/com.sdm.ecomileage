package com.sdm.ecomileage.model.search.response

data class SearchFeedInfoResponse(
    val code: Int,
    val message: String,
    val page: Int,
    val result: Result
)