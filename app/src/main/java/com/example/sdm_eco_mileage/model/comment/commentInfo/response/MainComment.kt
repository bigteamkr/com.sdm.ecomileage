package com.example.sdm_eco_mileage.model.comment.commentInfo.response

data class MainComment(
    val commentsno: Int,
    val parentcommentsno: Int,
    val profileimg: String,
    val title: String,
    val userId: String,
    val userName: String
)