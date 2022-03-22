package com.sdm.ecomileage.model.homeAdd.request

data class NewActivityInfo(
    val category: String,
    val hashtag: List<String>,
    val imageList: List<Image>,
    val feedsno: Int,
    val content: String,
    val title: String,
    val userid: String
)