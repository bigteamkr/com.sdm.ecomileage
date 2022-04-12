package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.feedLike.request.FeedLikeRequest
import com.sdm.ecomileage.model.feedLike.response.FeedLikeResponse
import com.sdm.ecomileage.model.homeAdd.request.HomeAddRequest
import com.sdm.ecomileage.model.homeAdd.response.HomeAddResponse
import com.sdm.ecomileage.model.homeInfo.request.HomeInfoRequest
import com.sdm.ecomileage.model.homeInfo.response.HomeInfoResponse
import com.sdm.ecomileage.model.report.feed.request.ReportRequest
import com.sdm.ecomileage.model.report.feed.response.ReportResponse
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface HomeInfoAPI {
    @POST(value = "HomeInfo")
    suspend fun getHomeInfo(
        @Header("token") token: String,
        @Body body: HomeInfoRequest
    ): HomeInfoResponse

    @POST(value = "NewActivityInfo")
    suspend fun postHomeFeed(
        @Header("token") token: String,
        @Body body: HomeAddRequest
    ): HomeAddResponse

    @POST(value = "FeedLike")
    suspend fun postFeedLike(
        @Header("token") token: String,
        @Body body:FeedLikeRequest
    ) : FeedLikeResponse

    @POST(value = "NewReportInfo")
    suspend fun postReport(
        @Header("token") token: String,
        @Body body: ReportRequest
    ) : ReportResponse
}