package com.sdm.ecomileage.repository.appSettingsRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.appMemberInfo.request.AppMemberInfoRequest
import com.sdm.ecomileage.model.appMemberInfo.response.AppMemberInfoResponse
import com.sdm.ecomileage.network.AppSettingsAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AppSettingsRepository @Inject constructor(private val api: AppSettingsAPI) {
    suspend fun getAppMemberInfo(
        token: String,
        body: AppMemberInfoRequest
    ): DataOrException<AppMemberInfoResponse, Boolean, Exception> {
        val response = try {
            api.getAppMemberInfo(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("AppSettingsRepository", "getAppMemberInfo: api call in repository didn't work")
            Log.d("AppSettingsRepository", "getAppMemberInfo: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }
}