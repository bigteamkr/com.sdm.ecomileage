package com.sdm.ecomileage.model.registerPage.searchLocation.request

data class SearchLocalArea(
    val key: String,
    val page: Int,
    val perpage: Int,
    val type: String,
    val uuid: String
)