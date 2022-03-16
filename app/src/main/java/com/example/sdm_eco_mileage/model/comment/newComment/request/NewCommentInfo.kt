package com.example.sdm_eco_mileage.model.comment.newComment.request

data class NewCommentInfo(
    val commentcontent: String,
    val feedsno: Int,
    val lang: String,
    val uuid: String
)