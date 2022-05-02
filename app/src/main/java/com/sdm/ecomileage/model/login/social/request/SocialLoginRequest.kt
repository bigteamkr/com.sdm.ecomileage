package com.sdm.ecomileage.model.login.social.request

data class SocialLoginRequest(
    val id: String,
    val userSSOID: String,
    val userSSOType: String,
    val uuid: String
)