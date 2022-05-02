package com.sdm.ecomileage.repository.findingPageRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.finding.id.request.FindingIdRequest
import com.sdm.ecomileage.model.finding.id.response.FindingIdResponse
import com.sdm.ecomileage.model.finding.password.request.FindingPasswordRequest
import com.sdm.ecomileage.model.finding.password.response.FindingPasswordResponse
import com.sdm.ecomileage.network.FindingAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class FindingRepository @Inject constructor(val api: FindingAPI) {
    suspend fun getFindingId(
        body: FindingIdRequest
    ): DataOrException<FindingIdResponse, Boolean, Exception> {
        val response = try {
            api.getFindingId(body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("FindingRepository", "getFindingId: api call in repository didn't work")
            Log.d("FindingRepository", "getFindingId: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun getFindingPassword(
        body: FindingPasswordRequest
    ): DataOrException<FindingPasswordResponse, Boolean, Exception> {
        val response = try {
            api.getFindingPassword(body)
        } catch (e: java.lang.Exception) {
            if (e is CancellationException)
                throw e

            Log.d("FindingRepository", "getFindingPassword: api call in repository didn't work")
            Log.d("FindingRepository", "getFindingPassword:  exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }
}