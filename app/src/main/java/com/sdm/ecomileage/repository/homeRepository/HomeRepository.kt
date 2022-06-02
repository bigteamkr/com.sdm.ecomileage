package com.sdm.ecomileage.repository.homeRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.feedLike.request.FeedLikeRequest
import com.sdm.ecomileage.model.feedLike.response.FeedLikeResponse
import com.sdm.ecomileage.model.homeAdd.request.HomeAddRequest
import com.sdm.ecomileage.model.homeAdd.response.HomeAddResponse
import com.sdm.ecomileage.model.homeInfo.request.HomeInfoRequest
import com.sdm.ecomileage.model.homeInfo.response.HomeInfoResponse
import com.sdm.ecomileage.model.report.feed.request.ReportRequest
import com.sdm.ecomileage.model.report.feed.response.ReportResponse
import com.sdm.ecomileage.network.HomeInfoAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: HomeInfoAPI) {
    suspend fun getHomeInfo(
        token: String,
        body: HomeInfoRequest
    ): DataOrException<HomeInfoResponse, Boolean, Exception> {
        val response = try {
            api.getHomeInfo(token = token, body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("HomeRepo", "getHomeInfo: api call in repository didn't work")
            Log.d("HomeRepo", "getHomeInfo: exception is $e")

            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }

    suspend fun postHomeFeed (
        token: String,
        body: HomeAddRequest
    ): DataOrException<HomeAddResponse, Boolean, Exception> {
        val response = try {
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

    suspend fun postFeedLike(
        token: String,
        body: FeedLikeRequest
    ): DataOrException<FeedLikeResponse, Boolean, Exception> {
        val response = try {
            api.postFeedLike(token = token, body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("HomeRepo", "postFeedLike: api call in repository didn't work")
            Log.d("HomeRepo", "postFeedLike: exception is $e")

            return DataOrException(e = e)
        }

        Log.d("HomeRepo", "postFeedLike: ${response.code}, ${response.message}")
        return DataOrException(data = response)
    }

    suspend fun postReport(
        token: String,
        body: ReportRequest
    ) : DataOrException<ReportResponse, Boolean, Exception>{
        val response = try {
            api.postReport(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("HomeRepo", "postReport: api call in repository didn't work")
            Log.d("HomeRepo", "postReport: exception is $e")

             return DataOrException(e = e)
        }

        Log.d("HomeRepo", "postReport: ${response.code}, ${response.message}")
        return DataOrException(data = response)
    }
}