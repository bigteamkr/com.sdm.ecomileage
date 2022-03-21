package com.example.sdm_eco_mileage.repository.HomeRepository

import android.util.Log
import com.example.sdm_eco_mileage.data.DataOrException
import com.example.sdm_eco_mileage.model.homeAdd.request.HomeAddRequest
import com.example.sdm_eco_mileage.model.homeAdd.response.HomeAddResponse
import com.example.sdm_eco_mileage.model.homeInfo.request.HomeInfoRequest
import com.example.sdm_eco_mileage.model.homeInfo.response.HomeInfoResponse
import com.example.sdm_eco_mileage.network.HomeInfoAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: HomeInfoAPI) {
    suspend fun getHomeInfo(token: String, body: HomeInfoRequest): DataOrException<HomeInfoResponse, Boolean, Exception> {
        val response = try{
            api.getHomeInfo(token = token, body = body)
        } catch(e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("HomeRepo", "getHomeInfo: api call in repository didn't work")
            Log.d("HomeRepo", "getHomeInfo: exception is $e")

            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }

    suspend fun postHomeFeed(token: String, body: HomeAddRequest) : DataOrException<HomeAddResponse, Boolean, Exception> {
        val  response = try {
            api.postHomeFeed(token = token, body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("HomeRepo", "postHomeFeed: api call in repository didn't work")
            Log.d("HomeRepo", "postHomeFeed: exception is $e")

            return DataOrException(e = e)
        }

        Log.d("HomeRepo", "postHomeFeed: ${response.code}, ${response.message}")
        return DataOrException(data = response)
    }
}