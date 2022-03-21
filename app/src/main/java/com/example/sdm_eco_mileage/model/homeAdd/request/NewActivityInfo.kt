package com.example.sdm_eco_mileage.model.homeAdd.request

data class NewActivityInfo(
    val category: String,
    val content: String,
    val hashtag: List<String>,
    val imageList: List<Image>,
    val sno: Int,
    val title: String,
    val userid: String
)