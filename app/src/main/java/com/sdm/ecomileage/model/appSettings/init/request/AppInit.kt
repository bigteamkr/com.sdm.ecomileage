package com.sdm.ecomileage.model.appSettings.init.request

data class AppInit(
    val appVersion: Int,
    val osType: Int,
    val osVersion: String,
    val refreshToken: String,
    val uuid: String
)