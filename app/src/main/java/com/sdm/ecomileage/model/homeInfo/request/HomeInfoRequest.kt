package com.sdm.ecomileage.model.homeInfo.request

data class HomeInfoRequest(
    val HomeInfo: List<HomeInfo>,
    val postpage: Int = 1,
    val postsize: Int = 10,
    val friendpage: Int = 1,
    val friendperpage: Int = 10
)