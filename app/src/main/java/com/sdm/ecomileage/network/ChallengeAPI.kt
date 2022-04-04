package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.challenge.newChallenge.newChallengeRequest.NewChallengeInfoRequest
import com.sdm.ecomileage.model.challenge.newChallenge.newChallengeResponse.NewChallengeInfoResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChallengeAPI {
    @POST("NewChallengeInfo")
    suspend fun postNewChallengeInfo(
        @Header ("token") token:String,
        @Body body : NewChallengeInfoRequest
    ) : NewChallengeInfoResponse
}