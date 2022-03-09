package com.example.sdm_eco_mileage.network

import com.example.sdm_eco_mileage.model.homeInfo.request.HomeInfoRequest
import com.example.sdm_eco_mileage.model.homeInfo.response.HomeInfoResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface HomeInfoAPI {
    @POST(value = "HomeInfo")
    suspend fun postHomeInfo(
        @Header("token") token: String,
        @Body body: HomeInfoRequest
    ): HomeInfoResponse
}