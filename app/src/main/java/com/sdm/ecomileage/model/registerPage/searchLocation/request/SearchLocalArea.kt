package com.sdm.ecomileage.model.registerPage.searchLocation.request

data class SearchLocalArea(
    val key: String,
    val page: Int = 1,
    val perpage: Int = 20,
    val type: String,
    val uuid: String
)