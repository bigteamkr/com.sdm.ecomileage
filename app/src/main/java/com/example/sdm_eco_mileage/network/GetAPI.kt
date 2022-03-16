package com.example.sdm_eco_mileage.network

import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface GetAPI {

    @GET(value = "profile/img")
    suspend fun getProfileImg(
        @Query("userid") userid: String
    ): String


}