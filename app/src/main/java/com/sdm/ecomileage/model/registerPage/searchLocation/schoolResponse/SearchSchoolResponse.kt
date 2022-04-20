package com.sdm.ecomileage.model.registerPage.searchLocation.schoolResponse

data class SearchSchoolResponse(
    val code: Int,
    val message: String,
    val page: Int,
    val result: Result,
    val type: String
)