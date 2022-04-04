package com.sdm.ecomileage.repository.challengeRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.challenge.newChallenge.newChallengeRequest.NewChallengeInfoRequest
import com.sdm.ecomileage.model.challenge.newChallenge.newChallengeResponse.NewChallengeInfoResponse
import com.sdm.ecomileage.network.ChallengeAPI
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class ChallengeRepository @Inject constructor(private val api: ChallengeAPI) {
    suspend fun postNewChallengeInfo(
        token: String,
        body: NewChallengeInfoRequest
    ): DataOrException<NewChallengeInfoResponse, Boolean, Exception> {
        val response = try {
            api.postNewChallengeInfo(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("ChallengeRepository", "postNewChallengeInfo: api call in repository didn't work")
            Log.d("ChallengeRepository", "postNewChallengeInfo: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }
}