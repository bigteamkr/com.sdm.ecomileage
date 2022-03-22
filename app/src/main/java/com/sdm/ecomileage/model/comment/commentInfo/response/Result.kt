package com.sdm.ecomileage.model.comment.commentInfo.response

data class Result(
    val mainComment: List<MainComment>,
    val subComment: List<Any>
)