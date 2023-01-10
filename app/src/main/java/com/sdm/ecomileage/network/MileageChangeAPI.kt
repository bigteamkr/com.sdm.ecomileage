package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.mileagechange.request.MileageChangeRequest
import com.sdm.ecomileage.model.mileagechange.response.MileageChangeResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface MileageChangeAPI {
    @POST("AppConvertMileageInfo")
    // @POST("MileageChangeInfo")
    suspend fun postMileageChangeInfo(
        @Header ("token") token:String,
        @Body body : MileageChangeRequest
    ) : MileageChangeResponse
}