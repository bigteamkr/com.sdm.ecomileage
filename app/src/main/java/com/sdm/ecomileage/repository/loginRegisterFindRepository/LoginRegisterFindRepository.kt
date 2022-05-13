package com.sdm.ecomileage.repository.loginRegisterFindRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
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
import com.sdm.ecomileage.model.naver.UserInfoResponse
import com.sdm.ecomileage.model.registerPage.register.request.RegisterRequest
import com.sdm.ecomileage.model.registerPage.register.response.RegisterResponse
import com.sdm.ecomileage.model.registerPage.searchLocation.areaResponse.SearchAreaResponse
import com.sdm.ecomileage.model.registerPage.searchLocation.request.SearchLocalAreaRequest
import com.sdm.ecomileage.model.registerPage.searchLocation.schoolResponse.SearchSchoolResponse
import com.sdm.ecomileage.model.registerPage.socialRegister.request.SocialRegisterRequest
import com.sdm.ecomileage.model.registerPage.socialRegister.response.SocialRegisterResponse
import com.sdm.ecomileage.network.LoginRegisterFindAPI
import com.sdm.ecomileage.network.NaverLoginAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class LoginRegisterFindRepository @Inject constructor(
    private val api: LoginRegisterFindAPI,
    private val naverApi: NaverLoginAPI
) {

    suspend fun postAppInit(
        body: AppInitRequest
    ): DataOrException<AppInitResponse, Boolean, java.lang.Exception> {
        val response = try {
            api.postAppInit(body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("LoginRegisterFindRepository", "postAppInit: api call in repository didn't work")
            Log.d("LoginRegisterFindRepository", "postAppInit: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun getLogin(
        body: LoginRequest
    ): DataOrException<LoginResponse, Boolean, Exception> {
        val response = try {
            api.getLogin(body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("LoginRegisterFindRepository", "getLogin: api call in repository didn't work")
            Log.d("LoginRegisterFindRepository", "getLogin: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun getAppRequestToken(
        body: AppRequestTokenRequest
    ): DataOrException<AppRequestTokenResponse, Boolean, Exception> {
        val response = try {
            api.getAppRequestToken(body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d(
                "LoginRegisterFindRepository",
                "getAppRequestToken: api call in repository didn't work"
            )
            Log.d("LoginRegisterFindRepository", "getAppRequestToken: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun postRegister(
        body: RegisterRequest
    ): DataOrException<RegisterResponse, Boolean, Exception> {
        val response = try {
            api.postRegister(body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("LoginRegisterFindRepository", "postRegister: api call in repository didn't work")
            Log.d("LoginRegisterFindRepository", "postRegister: exception is $e")

            return DataOrException(e = e)
        }

        return DataOrException(response)
    }

    suspend fun postSocialRegister(
        body: SocialRegisterRequest
    ): DataOrException<SocialRegisterResponse, Boolean, Exception> {
        val response = try {
            api.postSocialRegister(body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d(
                "LoginRegisterFindRepository",
                "postSocialRegister: api call in repository didn't work"
            )
            Log.d("LoginRegisterFindRepository", "postSocialRegister: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun putMemberUpdate(
        token: String,
        body: MemberUpdateRequest
    ): DataOrException<MemberUpdateResponse, Boolean, Exception> {
        val response = try {
            api.putMemberUpdate(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d(
                "LoginRegisterFindRepository",
                "putMemberUpdate: api call in repository didn't work"
            )
            Log.d("LoginRegisterFindRepository", "putMemberUpdate: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun getSocialLogin(
        body: SocialLoginRequest
    ): DataOrException<SocialLoginResponse, Boolean, Exception> {
        val response = try {
            api.getSocialLogin(body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d(
                "LoginRegisterFindRepository",
                "getSocialLogin: api call in repository didn't work"
            )
            Log.d("LoginRegisterFindRepository", "getSocialLogin: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun getSearchLocalArea(
        body: SearchLocalAreaRequest
    ): DataOrException<SearchAreaResponse, Boolean, Exception> {
        val response = try {
            api.getSearchLocalArea(body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d(
                "LoginRegisterFindRepository",
                "getSearchLocalArea: getSocialLogin: api call in repository didn't work"
            )
            Log.d(
                "LoginRegisterFindRepository",
                "getSearchLocalArea: getSocialLogin: exception is $e"
            )

            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun getSearchLocalSchool(
        body: SearchLocalAreaRequest
    ): DataOrException<SearchSchoolResponse, Boolean, Exception> {
        val response = try {
            api.getSearchLocalSchool(body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d(
                "LoginRegisterFindRepository",
                "getSearchLocalArea: getSocialLogin: api call in repository didn't work"
            )
            Log.d(
                "LoginRegisterFindRepository",
                "getSearchLocalArea: getSocialLogin: exception is $e"
            )
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun getNaverUserInfo(
        token: String
    ): DataOrException<UserInfoResponse, Boolean, Exception> {
        val response = try {
            naverApi.getUserInfo(token)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d(
                "LoginRegisterFindRepository",
                "getNaverUserInfo: api call in repository didn't work"
            )
            Log.d("LoginRegisterFindRepository", "getNaverUserInfo:  exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(response)
    }
}