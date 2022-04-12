package com.sdm.ecomileage.model.homeInfo.request

data class HomeInfo(
    val userid: String,
    val postpage: Int = 1,
    val postperpage: Int = 20,
    val friendpage: Int = 1,
    val friendperpage: Int = 20
)