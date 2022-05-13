package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.naver.UserInfoResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface NaverLoginAPI {
    @GET("v1/nid/me")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ) : UserInfoResponse
}