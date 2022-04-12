package com.sdm.ecomileage.model.appSettings.refresh.request

data class AppRequestToken(
    val refreshToken: String,
    val uuid: String
)