package com.example.sdm_eco_mileage.model.comment.newComment.request

data class NewCommentInfo(
    val commentcontent: String,
    val commenthashtag: List<String>?,
    val commentsno: Int?,
    val feedsno: Int,
    val lang: String,
    val parentcommentsno: Int?,
    val uuid: String
)