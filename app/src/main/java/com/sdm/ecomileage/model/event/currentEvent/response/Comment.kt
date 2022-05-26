package com.sdm.ecomileage.model.event.currentEvent.response

data class Comment(
    val commentcontent: String,
    val commentwriter: String,
    val commentwritername: String,
    val eventcommentsno: Int,
    val eventid: String,
    val eventsno: Int,
    val insertdate: Long,
    val insertuserid: String,
    val profileimg: String
)