package com.sdm.ecomileage.model.appSettings.init.response

data class ServerInfo(
    val appType: Int,
    val appVersion: Int,
    val isRequired: Boolean
)