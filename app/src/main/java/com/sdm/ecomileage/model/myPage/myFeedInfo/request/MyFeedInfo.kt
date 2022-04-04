package com.sdm.ecomileage.model.myPage.myFeedInfo.request

data class MyFeedInfo(
    val desc: String,
    val lang: String,
    val order: String?,
    val page: Int?,
    val perpage: Int?,
    val uuid: String
)