package com.sdm.ecomileage.model.event.newEventComment.request

data class NewEventCommentInfo(
    val eventcommentcontent: String,
    val eventcommentsno: Int,
    val eventid: String,
    val uuid: String
)