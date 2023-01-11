package com.sdm.ecomileage.model.mileagechange.request

data class MileageChangeRequest(

    val uuid: String,
    val usagetype: String,
    val gifttype: String?,     // 00 전자, 10 지류
    val persno: String
)
