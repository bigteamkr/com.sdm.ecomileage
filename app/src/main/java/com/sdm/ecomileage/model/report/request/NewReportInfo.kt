package com.sdm.ecomileage.model.report.request

data class NewReportInfo(
    val feedsno: Any,
    val reportcontent: String?,
    val reporttype: String,
    val reportyn: Any,
    val uuid: String
)