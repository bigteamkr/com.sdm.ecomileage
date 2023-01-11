package com.sdm.ecomileage.repository.mileageChangeRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.mileagechange.request.MileageChangeRequest
import com.sdm.ecomileage.model.mileagechange.response.MileageChangeResponse
import com.sdm.ecomileage.network.MileageChangeAPI
import dagger.Provides

import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

class MileageChangeRepository @Inject constructor(
    private val api: MileageChangeAPI
    )
    {
        @Singleton
        suspend fun postMileageChangeRepo(
            token: String,
            body: MileageChangeRequest
        ): DataOrException<MileageChangeResponse, Boolean, Exception> {
        val response = try {
            api.postMileageChangeAPI(token, body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("MileageChangeRepository", "postMilageChange: api call in repository didn't work")
            Log.d("MileageChangeRepository", "postMileageChange: exception is $e")

            return DataOrException(e = e)
        }

        return DataOrException(response)
    }
}