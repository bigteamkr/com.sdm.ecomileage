package com.sdm.ecomileage.model.event.newEventJoin.request

data class NewEventInfo(
    val eventid: String,
    val lang: String,
    val userid: String,
    val uuid: String
)