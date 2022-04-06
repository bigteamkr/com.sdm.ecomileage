package com.sdm.ecomileage.repository.myPageRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfoRequest
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import com.sdm.ecomileage.model.myPage.newFollow.request.NewFollowInfoRequest
import com.sdm.ecomileage.model.myPage.newFollow.response.NewFollowInfoResponse
import com.sdm.ecomileage.model.myPage.userFeedInfo.request.UserFeedInfoRequest
import com.sdm.ecomileage.model.myPage.userFeedInfo.response.UserFeedInfoResponse
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

            Log.d("MyPageRepository", "getMyFeedInfo: api call in repository didn't work")
            Log.d("MyPageRepository", "getMyFeedInfo: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun getUserFeedInfo(
        token: String,
        body: UserFeedInfoRequest
    ): DataOrException<UserFeedInfoResponse, Boolean, Exception> {
        val response = try {
            api.getUserFeedInfo(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("MyPageRepository", "getUserFeedInfo: api call in repository didn't work")
            Log.d("MyPageRepository", "getUserFeedInfo: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun putNewFollowInfo(
        token: String,
        body: NewFollowInfoRequest
    ): DataOrException<NewFollowInfoResponse, Boolean, Exception> {
        val response = try {
            api.putNewFollowInfo(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("MyPageRepository", "putNewFollowInfo: api call in repository didn't work")
            Log.d("MyPageRepository", "putNewFollowInfo: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

}