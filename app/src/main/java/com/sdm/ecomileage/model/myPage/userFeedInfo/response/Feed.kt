package com.sdm.ecomileage.model.myPage.userFeedInfo.response

data class Feed(
    val reportuseryn: Boolean,
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