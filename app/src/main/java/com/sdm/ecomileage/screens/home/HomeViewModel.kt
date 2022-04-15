package com.sdm.ecomileage.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.feedLike.request.FeedLike
import com.sdm.ecomileage.model.feedLike.request.FeedLikeRequest
import com.sdm.ecomileage.model.feedLike.response.FeedLikeResponse
import com.sdm.ecomileage.model.homeInfo.request.HomeInfo
import com.sdm.ecomileage.model.homeInfo.request.HomeInfoRequest
import com.sdm.ecomileage.model.homeInfo.response.HomeInfoResponse
import com.sdm.ecomileage.model.homeInfo.response.Post
import com.sdm.ecomileage.model.report.feed.request.NewReportInfo
import com.sdm.ecomileage.model.report.feed.request.ReportRequest
import com.sdm.ecomileage.model.report.feed.response.ReportResponse
import com.sdm.ecomileage.network.HomeInfoAPI
import com.sdm.ecomileage.repository.homeRepository.HomeRepository
import com.sdm.ecomileage.repository.paging.MainFeedPagingSource
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import com.sdm.ecomileage.utils.loginedUserIdUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val api: HomeInfoAPI
) : ViewModel() {

    suspend fun getHomeInfo(): DataOrException<HomeInfoResponse, Boolean, Exception> =
        repository.getHomeInfo(
            accessTokenUtil, HomeInfoRequest(
                HomeInfo = listOf(
                    HomeInfo(
                        loginedUserIdUtil
                    )
                )
            )
        )

    val pager = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 5),
        pagingSourceFactory = { MainFeedPagingSource(api) }
    ).flow.cachedIn(viewModelScope)


    private val _reportingFeedNoList = mutableMapOf<Int, String>()
    fun getReportingFeedNoValueFromKey(key: Int) = _reportingFeedNoList[key]

    fun reportingFeedAdd(feedsNo: Int, reportType: String) {
        _reportingFeedNoList[feedsNo] = reportType
    }

    fun reportingFeedNoRemove(feedsNo: Int) {
        _reportingFeedNoList.remove(feedsNo)
    }

    fun isFeedIncludedReportingList(feedsNo: Int): Boolean = _reportingFeedNoList.contains(feedsNo)

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    suspend fun refresh(origin: List<Post>): List<Post> {
        var list = origin
        viewModelScope.launch {
            _isRefreshing.emit(true)
            getHomeInfo().data?.result?.postList?.let {
                list = it
                _isRefreshing.emit(false)
            } ?: _isRefreshing.emit(false)
        }
        return list
    }

    suspend fun postReport(
        feedsNo: Int,
        reportType: String,
        reportContent: String? = null,
        reportYN: Boolean
    ): DataOrException<ReportResponse, Boolean, Exception> =
        repository.postReport(
            accessTokenUtil,
            ReportRequest(
                NewReportInfo = listOf(
                    NewReportInfo(
                        uuid = currentUUIDUtil,
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
            accessTokenUtil, FeedLikeRequest(
                FeedLikes = listOf(
                    FeedLike(
                        lang = "ko",
                        uuid = currentUUIDUtil,
                        feedsno = feedsNo,
                        likeyn = likeYN
                    )
                )
            )
        )
}