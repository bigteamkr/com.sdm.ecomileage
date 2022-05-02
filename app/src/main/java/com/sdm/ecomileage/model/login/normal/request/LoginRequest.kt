package com.sdm.ecomileage.model.login.normal.request

data class LoginRequest(
    val id: String,
    val password: String,
    val uuid: String
)