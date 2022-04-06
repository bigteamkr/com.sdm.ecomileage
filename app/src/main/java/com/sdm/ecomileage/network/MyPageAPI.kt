package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfoRequest
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import com.sdm.ecomileage.model.myPage.newFollow.request.NewFollowInfoRequest
import com.sdm.ecomileage.model.myPage.newFollow.response.NewFollowInfoResponse
import com.sdm.ecomileage.model.myPage.userFeedInfo.request.UserFeedInfoRequest
import com.sdm.ecomileage.model.myPage.userFeedInfo.response.UserFeedInfoResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MyPageAPI {
    @POST("MyFeedInfo")
    suspend fun getMyFeedInfo(
        @Header("token") token: String,
        @Body body:MyFeedInfoRequest
    ) : MyFeedInfoResponse

    @POST("UserFeedInfo")
    suspend fun getUserFeedInfo(
        @Header ("token") token: String,
        @Body body: UserFeedInfoRequest
    ) : UserFeedInfoResponse

    @POST("NewFollowInfo")
    suspend fun putNewFollowInfo(
        @Header ("token") token: String,
        @Body body: NewFollowInfoRequest
    ) : NewFollowInfoResponse
}