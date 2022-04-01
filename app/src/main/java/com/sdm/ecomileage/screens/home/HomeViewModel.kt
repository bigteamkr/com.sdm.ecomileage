package com.sdm.ecomileage.screens.home

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.feedLike.request.FeedLike
import com.sdm.ecomileage.model.feedLike.request.FeedLikeRequest
import com.sdm.ecomileage.model.feedLike.response.FeedLikeResponse
import com.sdm.ecomileage.model.homeInfo.request.HomeInfo
import com.sdm.ecomileage.model.homeInfo.request.HomeInfoRequest
import com.sdm.ecomileage.model.homeInfo.response.HomeInfoResponse
import com.sdm.ecomileage.model.report.request.NewReportInfo
import com.sdm.ecomileage.model.report.request.ReportRequest
import com.sdm.ecomileage.model.report.response.ReportResponse
import com.sdm.ecomileage.repository.homeRepository.HomeRepository
import com.sdm.ecomileage.utils.accessToken
import com.sdm.ecomileage.utils.loginedUserId
import com.sdm.ecomileage.utils.uuidSample
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    suspend fun getHomeInfo(): DataOrException<HomeInfoResponse, Boolean, Exception> =
        repository.getHomeInfo(
            accessToken, HomeInfoRequest(
                HomeInfo = listOf(
                    HomeInfo(loginedUserId)
                )
            )
        )

    private val _reportingFeedNoList = mutableMapOf<Int, String>()
    fun getReportingFeedNoValueFromKey(key: Int) = _reportingFeedNoList[key]

    fun reportingFeedAdd(feedsNo: Int, reportType: String) {
        _reportingFeedNoList[feedsNo] = reportType
    }

    fun reportingFeedNoRemove(feedsNo: Int) {
        _reportingFeedNoList.remove(feedsNo)
    }

    fun isFeedIncludedReportingList(feedsNo: Int): Boolean = _reportingFeedNoList.contains(feedsNo)

    suspend fun postReport(
        feedsNo: Int,
        reportType: String,
        reportContent: String? = null,
        reportYN: Boolean
    ): DataOrException<ReportResponse, Boolean, Exception> =
        repository.postReport(
            accessToken,
            ReportRequest(
                NewReportInfo = listOf(
                    NewReportInfo(
                        uuid = uuidSample,
                        feedsno = feedsNo,
                        reporttype = reportType,
                        reportcontent = reportContent,
                        reportyn = reportYN
                    )
                )
            )
        )


    suspend fun postFeedLike(
        feedsNo: Int,
        likeYN: Boolean
    ): DataOrException<FeedLikeResponse, Boolean, Exception> =
        repository.postFeedLike(
            accessToken, FeedLikeRequest(
                FeedLikes = listOf(
                    FeedLike(
                        lang = "ko",
                        uuid = uuidSample,
                        feedsno = feedsNo,
                        likeyn = likeYN
                    )
                )
            )
        )
}