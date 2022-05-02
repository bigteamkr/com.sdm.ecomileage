package com.sdm.ecomileage.model.login.normal.response

data class LoginResponse(
    val code: Int,
    val `data`: Data,
    val message: String
)