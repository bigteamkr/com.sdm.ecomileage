package com.sdm.ecomileage.model.homeInfo.response

data class Post(
    val imageList: List<String>,
    val reportyn: Boolean,
    val likeyn: Boolean,
    val profileimg: String,
    val hashtags: List<String>,
    val commentCount: Int,
    val likeCount: Int,
    val photo: String,
    val feedcontent: String,
    val feedtitle: String,
    val userName: String,
    val userid: String,
    val feedsno: Int
)