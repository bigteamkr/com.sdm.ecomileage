package com.sdm.ecomileage.model.homedetail.comment.commentInfo.response

data class MainComment(
    val commentsno: Int,
    val parentcommentsno: Int,
    val userId: String,
    val profileimg: String,
    val userName: String,
    val photo: String,
    val title: String
)