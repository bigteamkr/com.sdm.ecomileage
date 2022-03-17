package com.example.sdm_eco_mileage.model.homeInfo.request

import com.google.gson.annotations.SerializedName

data class HomeInfoRequest(
    val HomeInfo: List<HomeInfo>,
    val postpage: Int = 1,
    val postsize: Int = 10
)