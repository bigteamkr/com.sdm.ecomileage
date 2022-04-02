package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.login.request.LoginRequest
import com.sdm.ecomileage.model.login.response.LoginResponse
import com.sdm.ecomileage.model.memberUpdate.request.MemberUpdateRequest
import com.sdm.ecomileage.model.memberUpdate.response.MemberUpdateResponse
import com.sdm.ecomileage.model.register.request.RegisterRequest
import com.sdm.ecomileage.model.register.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Header
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

    @POST(value = "/app/AppMemberUpdate")
    suspend fun putMemberUpdate(
        @Header ("token") token:String,
        @Body body: MemberUpdateRequest
    ) : MemberUpdateResponse
}