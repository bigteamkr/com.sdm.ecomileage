package com.sdm.ecomileage.model.myPage.userHistoryInfo.request

data class AppHistoryInfo(
    val mileType: String,
    val page: Int,
    val perpage: Int,
    val searchMonths: Int,
    val uuid: String
)