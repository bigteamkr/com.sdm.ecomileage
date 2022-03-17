package com.example.sdm_eco_mileage.model.homeInfo.response

data class Post(
    val commentCount: Int,
    val hashtags: List<String>,
    val likeCount: Int,
    val photo: String,
    val feedcontent: String,
    val feedtitle: String,
    val userName: String,
    val userid: String,
    val id: Int
)