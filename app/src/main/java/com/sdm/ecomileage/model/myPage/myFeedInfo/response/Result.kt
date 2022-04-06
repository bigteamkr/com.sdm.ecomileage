package com.sdm.ecomileage.model.myPage.myFeedInfo.response

data class Result(
    val feedList: List<Feed>,
    val point: Int,
    val userid: String,
    val username: String
)