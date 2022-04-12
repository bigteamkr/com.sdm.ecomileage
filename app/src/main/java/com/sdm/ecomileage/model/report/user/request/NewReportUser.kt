package com.sdm.ecomileage.model.report.user.request

data class NewReportUser(
    val reportcontent: String,
    val reportid: String,
    val reporttype: String,
    val reportyn: Boolean,
    val uuid: String
)