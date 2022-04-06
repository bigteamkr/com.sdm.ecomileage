package com.sdm.ecomileage.model.myPage.userFeedInfo.response

data class UserFeedInfoResponse(
    val code: Int,
    val message: String,
    val page: Int,
    val result: Result
)