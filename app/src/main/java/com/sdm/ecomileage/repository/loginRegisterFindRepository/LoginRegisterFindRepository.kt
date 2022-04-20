package com.sdm.ecomileage.repository.loginRegisterFindRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.appSettings.init.request.AppInitRequest
import com.sdm.ecomileage.model.appSettings.init.response.AppInitResponse
import com.sdm.ecomileage.model.appSettings.refresh.request.AppRequestTokenRequest
import com.sdm.ecomileage.model.appSettings.refresh.response.AppRequestTokenResponse
import com.sdm.ecomileage.model.login.request.LoginRequest
import com.sdm.ecomileage.model.login.response.LoginResponse
import com.sdm.ecomileage.model.memberUpdate.request.MemberUpdateRequest
import com.sdm.ecomileage.model.memberUpdate.response.MemberUpdateResponse
import com.sdm.ecomileage.model.registerPage.register.request.RegisterRequest
import com.sdm.ecomileage.model.registerPage.register.response.RegisterResponse
import com.sdm.ecomileage.network.LoginRegisterFindAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class LoginRegisterFindRepository @Inject constructor(private val api: LoginRegisterFindAPI) {

    suspend fun postAppInit(
        body: AppInitRequest
    ) : DataOrException<AppInitResponse, Boolean, java.lang.Exception> {
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
    ) : DataOrException<AppRequestTokenResponse, Boolean, Exception> {
        val response = try {
            api.getAppRequestToken(body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("LoginRegisterFindRepository", "getAppRequestToken: api call in repository didn't work")
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



}