package com.sdm.ecomileage.screens.home

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.feedLike.request.FeedLike
import com.sdm.ecomileage.model.feedLike.request.FeedLikeRequest
import com.sdm.ecomileage.model.feedLike.response.FeedLikeResponse
import com.sdm.ecomileage.model.homeInfo.request.HomeInfo
import com.sdm.ecomileage.model.homeInfo.request.HomeInfoRequest
import com.sdm.ecomileage.model.homeInfo.response.HomeInfoResponse
import com.sdm.ecomileage.repository.HomeRepository.HomeRepository
import com.sdm.ecomileage.utils.accessToken
import com.sdm.ecomileage.utils.uuidSample
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    suspend fun getHomeInfo(): DataOrException<HomeInfoResponse, Boolean, Exception> =
        repository.getHomeInfo(
            accessToken, HomeInfoRequest(
                HomeInfo = listOf(
                    HomeInfo("admin@email.com")
                )
            )
        )

    suspend fun postFeedLike(feedsNo: Int, likeYN: Boolean): DataOrException<FeedLikeResponse, Boolean, Exception> =
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