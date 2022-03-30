package com.sdm.ecomileage.data

data class DataOrException<T, Boolean, E : Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)

data class Report(
    val reportFeedNo:Int,
    val reportType: String,
    val reportContent: String
)