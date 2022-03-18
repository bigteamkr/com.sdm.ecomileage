package com.example.sdm_eco_mileage.model.comment.commentInfo.request

data class CommentInfo(
    val commentsno: Int?,
    val feedsno: Int,
    val lang: String,
    val userid: String,
    val uuid: String
)