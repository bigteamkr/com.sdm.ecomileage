package com.sdm.ecomileage.model.myPage.myFeedInfo.response

data class Feed(
    val commentCount: Int,
    val dayflag: String,
    val feedcontent: String,
    val feedsno: Int,
    val feedtitle: String,
    val hashtags: List<String>,
    val imageList: List<String>,
    val likeCount: Int,
    val likeyn: Boolean,
    val photo: String,
    val profileimg: String,
    val reportyn: Boolean,
    val userName: String,
    val userid: String,
    val writedate: String
)