package com.sdm.ecomileage.model.report.comment.request

data class NewReportComment(
    val commentsno: Int,
    val feedsno: Int,
    val reportcontent: String,
    val reporttype: String,
    val reportyn: Boolean,
    val uuid: String
)