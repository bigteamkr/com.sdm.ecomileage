package com.sdm.ecomileage.model.appSettings.refresh.response

data class AppRequestTokenResponse(
    val code: Int,
    val message: String,
    val tokenInfo: TokenInfo
)