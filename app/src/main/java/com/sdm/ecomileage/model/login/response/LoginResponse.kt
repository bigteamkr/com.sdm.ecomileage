package com.sdm.ecomileage.model.login.response

data class LoginResponse(
    val code: Int,
    val `data`: Data,
    val message: String
)