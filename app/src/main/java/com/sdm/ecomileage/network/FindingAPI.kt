package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.finding.id.request.FindingIdRequest
import com.sdm.ecomileage.model.finding.id.response.FindingIdResponse
import com.sdm.ecomileage.model.finding.password.request.FindingPasswordRequest
import com.sdm.ecomileage.model.finding.password.response.FindingPasswordResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface FindingAPI {
    @POST("AppUserAccountFind")
    suspend fun getFindingId(
        @Body body: FindingIdRequest
    ) : FindingIdResponse

    @POST("AppUserAccountPasswordFind")
    suspend fun getFindingPassword(
        @Body body: FindingPasswordRequest
    ) : FindingPasswordResponse
}