package com.example.sdm_eco_mileage.model.homeAdd.request

import android.graphics.Bitmap

data class Image(
    val filedtlsno: Int,
    val filename: String,
    val filesno: Int,
    val image: String,
    val status: String,
    val type: String
)