package com.example.sdm_eco_mileage.model.homeInfo.response

data class Post(
    val commentCount: Int,
    val id: String,
    val likeCount: Int,
    val likeStatus: String,
    val photo: String,
    val title: String,
    val userName: String
)