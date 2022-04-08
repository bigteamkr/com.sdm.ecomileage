package com.sdm.ecomileage.model.homedetail.comment.report.request

data class NewReportComment(
    val commentsno: Int,
    val feedsno: Int,
    val reportcontent: String,
    val reporttype: String,
    val reportyn: Boolean,
    val uuid: String
)