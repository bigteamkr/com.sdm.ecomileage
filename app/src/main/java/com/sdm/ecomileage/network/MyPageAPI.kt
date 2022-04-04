package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfoRequest
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MyPageAPI {
    @POST("MyFeedInfo")
    suspend fun getMyFeedInfo(
        @Header("token") token: String,
        @Body body:MyFeedInfoRequest
    ) : MyFeedInfoResponse
}