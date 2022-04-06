package com.sdm.ecomileage.model.homedetail.mainFeed.response

data class PostInfo(
    val commentCount: Int,
    val feedcontent: String,
    val feedtitle: String,
    val hashtags: List<String>,
    val id: Int,
    val imageList: List<String>,
    val likeCount: Int,
    val photo: String,
    val profileimg: String,
    val userName: String,
    val userid: String
)