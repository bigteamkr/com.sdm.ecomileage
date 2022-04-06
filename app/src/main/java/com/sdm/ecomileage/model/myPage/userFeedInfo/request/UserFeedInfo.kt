package com.sdm.ecomileage.model.myPage.userFeedInfo.request

data class UserFeedInfo(
    val desc: String,
    val order: String,
    val page: Int,
    val perpage: Int,
    val userid: String,
    val uuid: String
)