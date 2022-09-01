package com.sdm.ecomileage.repository.myPageRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.myPage.deleteFeed.request.DeleteFeedRequest
import com.sdm.ecomileage.model.myPage.deleteFeed.response.DeleteFeedResponse
import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfoRequest
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import com.sdm.ecomileage.model.myPage.newFollow.request.NewFollowInfoRequest
import com.sdm.ecomileage.model.myPage.newFollow.response.NewFollowInfoResponse
import com.sdm.ecomileage.model.myPage.userFeedInfo.request.UserFeedInfoRequest
import com.sdm.ecomileage.model.myPage.userFeedInfo.response.UserFeedInfoResponse
import com.sdm.ecomileage.model.myPage.userHistoryInfo.request.UserHistoryInfoRequest
import com.sdm.ecomileage.model.myPage.userHistoryInfo.response.UserHistoryResponse
import com.sdm.ecomileage.model.report.user.request.NewUserReportRequest
import com.sdm.ecomileage.model.report.user.response.NewUserReportResponse
import com.sdm.ecomileage.network.MyPageAPI
import com.sdm.ecomileage.repository.paging.UserHistoryPagingSource
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

    suspend fun postNewUserReport(
        token: String,
        body: NewUserReportRequest
    ): DataOrException<NewUserReportResponse, Boolean, Exception> {
        val response = try {
            api.postNewUserReport(
                token, body
            )
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("MyPageRepository", "postNewUserReport: api call in repository didn't work")
            Log.d("MyPageRepository", "postNewUserReport: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun deleteMyFeed(
        token: String,
        body: DeleteFeedRequest
    ): DataOrException<DeleteFeedResponse, Boolean, Exception> {
        val response = try {
            api.deleteMyFeed(
                token, body
            )
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("MyPageRepository", "deleteMyFeed: api call in repository didn't work")
            Log.d("MyPageRepository", "deleteMyFeed: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun getUserHistory(
        token: String,
        body: UserHistoryInfoRequest
    ) : DataOrException<UserHistoryResponse, Boolean, Exception> {
        val response = try {
            api.getUserHistory(token, body)
        } catch (e: Exception) {
            if (e is CancellationException) throw e

            Log.d("MyPageRepository", "getUserHistory: api call in repository didn't work")
            Log.d("MyPageRepository", "getUserHistory: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(response)
    }
}