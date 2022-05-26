package com.sdm.ecomileage.model.event.currentEvent.response

data class EventInfo(
    val eventid: String,
    val eventnm: String,
    val eventsno: Int,
    val eventstatus: String,
    val eventtype: String,
    val point: Int,
    val pointyn: Boolean
)