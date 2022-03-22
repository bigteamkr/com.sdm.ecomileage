package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.login.request.LoginRequest
import com.sdm.ecomileage.model.login.response.LoginResponse
import com.sdm.ecomileage.model.register.request.RegisterRequest
import com.sdm.ecomileage.model.register.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRegisterFindAPI {
    @POST("AppLogin")
    suspend fun getLogin(
        @Body body: LoginRequest
    ): LoginResponse

    @POST(value = "/app/AppRegister")
    suspend fun postRegister(
        @Body body: RegisterRequest
    ) : RegisterResponse
}