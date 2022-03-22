package com.sdm.ecomileage.model.feedLike.request

data class FeedLike(
    val feedsno: Int,
    val lang: String,
    val likeyn: Boolean,
    val uuid: String
)