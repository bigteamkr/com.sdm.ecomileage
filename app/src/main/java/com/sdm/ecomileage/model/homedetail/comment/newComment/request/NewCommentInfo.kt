package com.sdm.ecomileage.model.homedetail.comment.newComment.request

data class NewCommentInfo(
    val commenthashtag: List<String>?,
    val commentcontent: String,
    val commentsno: Int?,
    val feedsno: Int,
    val lang: String,
    val uuid: String
)