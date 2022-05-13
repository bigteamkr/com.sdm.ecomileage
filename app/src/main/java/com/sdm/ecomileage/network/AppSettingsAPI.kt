package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.appMemberInfo.request.AppMemberInfoRequest
import com.sdm.ecomileage.model.appMemberInfo.response.AppMemberInfoResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AppSettingsAPI {
    @POST("AppMemberInfo")
    suspend fun getAppMemberInfo(
        @Header("token") token: String,
        @Body body: AppMemberInfoRequest
    ) : AppMemberInfoResponse
}