package com.sdm.ecomileage.model.homeInfo.response

data class Result(
    val friendList: List<Friend>,
    val postList: List<Post>,
    val userDept: String
)