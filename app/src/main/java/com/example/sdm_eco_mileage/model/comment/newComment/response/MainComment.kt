package com.example.sdm_eco_mileage.model.comment.newComment.response

data class MainComment(
    val commentsno: Int,
    val hashtag: List<String>,
    val parentcommentsno: Int,
    val title: String,
    val userId: String,
    val userName: String
)