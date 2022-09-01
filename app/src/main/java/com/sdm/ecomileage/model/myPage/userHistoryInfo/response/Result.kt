package com.sdm.ecomileage.model.myPage.userHistoryInfo.response

data class Result(
    val code: Int,
    val message: String,
    val mileList: List<Mile>,
    val point: Int,
    val userid: String,
    val visits: Int
)