package com.sdm.ecomileage.network

import com.google.android.play.core.install.model.InstallErrorCode
import com.sdm.ecomileage.model.mileagechange.request.MileageChangeRequest
import com.sdm.ecomileage.model.mileagechange.response.MileageChangeResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

interface MileageChangeAPI {
    // 마일리지 전환신청
    @POST("AppConvertMileageInfo")
    suspend fun postMileageChangeAPI(
        @Header ("token") token:String,
        @Body body : MileageChangeRequest
    ) : MileageChangeResponse
}