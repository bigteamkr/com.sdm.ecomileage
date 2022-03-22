package com.sdm.ecomileage.model.login.response

data class Data(
    val accessToken: String,
    val refreshToken: String,
    val uuid: String
)