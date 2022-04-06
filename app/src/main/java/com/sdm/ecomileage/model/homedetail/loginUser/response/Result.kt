package com.sdm.ecomileage.model.homedetail.loginUser.response

data class Result(
    val userAccount: String,
    val userAddress: String,
    val userDept: String,
    val userId: String,
    val userImg: String,
    val userName: String,
    val userPoint: Int
)