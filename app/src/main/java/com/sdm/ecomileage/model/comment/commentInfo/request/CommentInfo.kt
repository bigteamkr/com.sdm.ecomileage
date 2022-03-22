package com.sdm.ecomileage.model.comment.commentInfo.request

data class CommentInfo(
    val commentsno: Int,
    val feedsno: Int,
    val userid: String,
    val uuid: String,
    val lang: String
)