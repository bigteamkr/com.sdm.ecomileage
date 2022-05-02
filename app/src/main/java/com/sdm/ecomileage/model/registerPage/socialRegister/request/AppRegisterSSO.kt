package com.sdm.ecomileage.model.registerPage.socialRegister.request

data class AppRegisterSSO(
    val childagree: String,
    val email: String,
    val isinfoagree: String,
    val profileImg: String,
    val userAddress: String,
    val userDept: String,
    val userName: String,
    val userPwd: String,
    val userSSOID: String,
    val userSSOType: String,
    val userSchool: Int?,
    val userTown: String
)