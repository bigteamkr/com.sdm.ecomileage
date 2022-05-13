package com.sdm.ecomileage.model.search.request

data class SearchFeedInfo(
    val category: String,
    val desc: String,
    val order: String,
    val page: Any,
    val perpage: Any,
    val searchkeyword: String,
    val uuid: String
)