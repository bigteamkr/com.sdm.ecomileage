package com.example.sdm_eco_mileage.repository.HomeRepository

import android.util.Log
import com.example.sdm_eco_mileage.data.DataOrException
import com.example.sdm_eco_mileage.model.homeInfo.request.HomeInfoRequest
import com.example.sdm_eco_mileage.model.homeInfo.response.HomeInfoResponse
import com.example.sdm_eco_mileage.network.HomeInfoAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: HomeInfoAPI) {
    suspend fun postHomeInfo(token: String, body: HomeInfoRequest): DataOrException<HomeInfoResponse, Boolean, Exception> {
        val response = try{
            api.postHomeInfo(token = token, body = body)
        } catch(e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("HomeInfo", "postHomeInfo: api call in repository didn't work")
            Log.d("HomeInfo", "postHomeInfo: exception is $e")

            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }

}