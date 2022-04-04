package com.sdm.ecomileage.model.challenge.newChallenge.newChallengeRequest

data class NewChallengeInfo(
    val category: String,
    val challengesno: Int,
    val content: String,
    val hashtag: List<String>,
    val imageList: List<Any>,
    val userid: String,
    val uuid: String
)