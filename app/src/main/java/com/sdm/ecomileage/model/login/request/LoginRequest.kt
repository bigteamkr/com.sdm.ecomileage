package com.sdm.ecomileage.model.login.request

data class LoginRequest(
    val id: String,
    val password: String,
    val uuid: String
)