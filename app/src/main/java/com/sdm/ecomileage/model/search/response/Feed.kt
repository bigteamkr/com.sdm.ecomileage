package com.sdm.ecomileage.model.search.response

data class Feed(
    val commentCount: Int,
    val dayflag: String,
    val feedcontent: String,
    val feedtitle: String,
    val hashtags: List<String>,
    val id: Int,
    val likeCount: Int,
    val photo: String,
    val userName: String,
    val userid: String,
    val writedate: String
)