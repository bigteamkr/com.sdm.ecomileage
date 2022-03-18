package com.example.sdm_eco_mileage.network

import com.example.sdm_eco_mileage.model.login.request.LoginRequest
import com.example.sdm_eco_mileage.model.login.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginRegisterFindAPI {
    @POST("AppLogin")
    suspend fun getLogin(
        @Header("token") token: String,
        @Body body: LoginRequest
    ): LoginResponse
}