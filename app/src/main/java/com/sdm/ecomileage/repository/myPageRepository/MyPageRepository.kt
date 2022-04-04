package com.sdm.ecomileage.repository.myPageRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfoRequest
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import com.sdm.ecomileage.network.MyPageAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class MyPageRepository @Inject constructor(private val api: MyPageAPI) {

    suspend fun getMyFeedInfo(
        token: String,
        body: MyFeedInfoRequest
    ): DataOrException<MyFeedInfoResponse, Boolean, Exception> {
        val response = try {
            api.getMyFeedInfo(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("getMyFeedInfo", "getMyFeedInfo: api call in repository didn't work")
            Log.d("getMyFeedInfo", "getMyFeedInfo: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }


}