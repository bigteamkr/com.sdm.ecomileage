package com.sdm.ecomileage.model.registerPage.register.request

data class AppRegister(
    val phone: String,
    val profileImg: String,
    val childagree: String,
    val isinfoagree: String,
    val userAddress: String,
    val userDept: String,
    val email: String,
    val userPwd: String,
    val userName: String
)