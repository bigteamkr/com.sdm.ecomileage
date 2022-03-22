package com.sdm.ecomileage.model.register.request

data class AppRegister(
    val profileImg: String,
    val childagree: String,
    val isinfoagree: String,
    val userAddress: String,
    val userDept: String,
    val email: String,
    val userPwd: String,
    val userName: String
)