package com.example.sdm_eco_mileage.network

import com.example.sdm_eco_mileage.model.homeAdd.request.HomeAddRequest
import com.example.sdm_eco_mileage.model.homeAdd.response.HomeAddResponse
import com.example.sdm_eco_mileage.model.homeInfo.request.HomeInfoRequest
import com.example.sdm_eco_mileage.model.homeInfo.response.HomeInfoResponse
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface HomeInfoAPI {
    @POST(value = "HomeInfo")
    suspend fun getHomeInfo(
        @Header("token") token: String,
        @Body body: HomeInfoRequest
    ): HomeInfoResponse

    @POST(value = "NewActivityInfo")
    suspend fun postHomeFeed(
        @Header("token") token: String,
        @Body body: HomeAddRequest
    ): HomeAddResponse

}