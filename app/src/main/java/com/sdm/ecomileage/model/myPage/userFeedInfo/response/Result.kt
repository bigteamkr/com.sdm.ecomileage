package com.sdm.ecomileage.model.myPage.userFeedInfo.response

data class Result(
    val feedList: List<Feed>,
    val followyn: Boolean,
    val userid: String,
    val username: String
)