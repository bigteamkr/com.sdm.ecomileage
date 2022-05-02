package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.appSettings.init.request.AppInitRequest
import com.sdm.ecomileage.model.appSettings.init.response.AppInitResponse
import com.sdm.ecomileage.model.appSettings.refresh.request.AppRequestTokenRequest
import com.sdm.ecomileage.model.appSettings.refresh.response.AppRequestTokenResponse
import com.sdm.ecomileage.model.login.normal.request.LoginRequest
import com.sdm.ecomileage.model.login.normal.response.LoginResponse
import com.sdm.ecomileage.model.login.social.request.SocialLoginRequest
import com.sdm.ecomileage.model.login.social.response.SocialLoginResponse
import com.sdm.ecomileage.model.memberUpdate.request.MemberUpdateRequest
import com.sdm.ecomileage.model.memberUpdate.response.MemberUpdateResponse
import com.sdm.ecomileage.model.registerPage.register.request.RegisterRequest
import com.sdm.ecomileage.model.registerPage.register.response.RegisterResponse
import com.sdm.ecomileage.model.registerPage.searchLocation.areaResponse.SearchAreaResponse
import com.sdm.ecomileage.model.registerPage.searchLocation.request.SearchLocalAreaRequest
import com.sdm.ecomileage.model.registerPage.searchLocation.schoolResponse.SearchSchoolResponse
import com.sdm.ecomileage.model.registerPage.socialRegister.request.SocialRegisterRequest
import com.sdm.ecomileage.model.registerPage.socialRegister.response.SocialRegisterResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginRegisterFindAPI {
    @POST("AppInit")
    suspend fun postAppInit(
        @Body body: AppInitRequest
    ): AppInitResponse

    @POST("AppLogin")
    suspend fun getLogin(
        @Body body: LoginRequest
    ): LoginResponse

    @POST("AppRequestToken")
    suspend fun getAppRequestToken(
        @Body body: AppRequestTokenRequest
    ): AppRequestTokenResponse

    @POST(value = "AppRegister")
    suspend fun postRegister(
        @Body body: RegisterRequest
    ): RegisterResponse

    @POST(value = "AppMemberUpdate")
    suspend fun putMemberUpdate(
        @Header("token") token: String,
        @Body body: MemberUpdateRequest
    ): MemberUpdateResponse

    @POST(value = "AppLoginSSO")
    suspend fun getSocialLogin(
        @Body body: SocialLoginRequest
    ) : SocialLoginResponse

    @POST(value = "AppRegisterSSO")
    suspend fun postSocialRegister(
        @Body body: SocialRegisterRequest
    ) : SocialRegisterResponse

    @POST(value = "SearchLocalArea")
    suspend fun getSearchLocalArea(
        @Body body: SearchLocalAreaRequest
    ) : SearchAreaResponse

    @POST(value = "SearchLocalArea")
    suspend fun getSearchLocalSchool(
        @Body body: SearchLocalAreaRequest
    ) : SearchSchoolResponse
}