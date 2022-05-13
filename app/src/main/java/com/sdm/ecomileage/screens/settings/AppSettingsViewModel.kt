package com.sdm.ecomileage.screens.settings

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.appMemberInfo.request.AppMemberInfo
import com.sdm.ecomileage.model.appMemberInfo.request.AppMemberInfoRequest
import com.sdm.ecomileage.model.appMemberInfo.response.AppMemberInfoResponse
import com.sdm.ecomileage.repository.appSettingsRepository.AppSettingsRepository
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel @Inject constructor(
    private val repository: AppSettingsRepository
) : ViewModel() {

    suspend fun getAppMemberInfo(): DataOrException<AppMemberInfoResponse, Boolean, Exception> =
        repository.getAppMemberInfo(
            accessTokenUtil, AppMemberInfoRequest(
                AppMemberInfo = listOf(
                    AppMemberInfo(
                        currentUUIDUtil
                    )
                )
            )
        )

}