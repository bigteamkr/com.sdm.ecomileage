package com.sdm.ecomileage.data

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AppSettings(
    val uuid: String = "0",
    val isThisFirstInit: Boolean = true,
    val lastLoginId: String = "",
    val refreshToken: String = "",
    val isSaveId: Boolean = false,
    val isAutoLogin: Boolean = false,
)