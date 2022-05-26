package com.sdm.ecomileage.model.event.currentEvent.response

data class Result(
    val attnList: List<Attn>,
    val commentList: List<Comment>,
    val eventInfo: EventInfo
)