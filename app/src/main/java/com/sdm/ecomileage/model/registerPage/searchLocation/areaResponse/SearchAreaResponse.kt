package com.sdm.ecomileage.model.registerPage.searchLocation.areaResponse

data class SearchAreaResponse(
    val code: Int,
    val message: String,
    val page: Int,
    val result: Result,
    val type: String
)