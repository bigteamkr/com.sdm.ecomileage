package com.example.sdm_eco_mileage.model.homeInfo.response

data class Post(
    val commentCount: Int,
    val hashtags: List<String>,
    val id: Int,
    val likeCount: Int,
    val photo: String,
    val userName: String,
    val userid: String
)