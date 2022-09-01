package com.sdm.ecomileage.screens.myPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.myPage.deleteFeed.request.DeleteFeedRequest
import com.sdm.ecomileage.model.myPage.deleteFeed.request.RemoveActivityInfo
import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfo
import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfoRequest
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import com.sdm.ecomileage.model.myPage.newFollow.request.NewFollowInfo
import com.sdm.ecomileage.model.myPage.newFollow.request.NewFollowInfoRequest
import com.sdm.ecomileage.model.myPage.userFeedInfo.request.UserFeedInfo
import com.sdm.ecomileage.model.myPage.userFeedInfo.request.UserFeedInfoRequest
import com.sdm.ecomileage.model.myPage.userFeedInfo.response.UserFeedInfoResponse
import com.sdm.ecomileage.model.myPage.userHistoryInfo.request.AppHistoryInfo
import com.sdm.ecomileage.model.myPage.userHistoryInfo.request.UserHistoryInfoRequest
import com.sdm.ecomileage.model.report.user.request.NewReportUser
import com.sdm.ecomileage.model.report.user.request.NewUserReportRequest
import com.sdm.ecomileage.network.MyPageAPI
import com.sdm.ecomileage.repository.myPageRepository.MyPageRepository
import com.sdm.ecomileage.repository.paging.MainFeedPagingSource
import com.sdm.ecomileage.repository.paging.UserHistoryPagingSource
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val repository: MyPageRepository, private val api: MyPageAPI) : ViewModel() {

    suspend fun getMyFeedInfo(): DataOrException<MyFeedInfoResponse, Boolean, Exception> =
        repository.getMyFeedInfo(
            accessTokenUtil, MyFeedInfoRequest(
                listOf(
                    MyFeedInfo(
                        lang = "ko",
                        uuid = currentUUIDUtil,
                        page = 1,
                        perpage = 10,
                        order = "user",
                        desc = "10"
                    )
                )
            )
        )

    suspend fun getUserFeedInfo(userId: String): DataOrException<UserFeedInfoResponse, Boolean, Exception> =
        repository.getUserFeedInfo(
            accessTokenUtil, UserFeedInfoRequest(
                listOf(
                    UserFeedInfo(
                        uuid = currentUUIDUtil,
                        userid = userId,
                        page = 1,
                        perpage = 10,
                        order = "user",
                        desc = "10"
                    )
                )
            )
        )

    suspend fun putNewFollowInfo(uuid: String, followId: String, followYN: Boolean) =
        repository.putNewFollowInfo(
            accessTokenUtil, NewFollowInfoRequest(
                listOf(
                    NewFollowInfo(
                        uuid = uuid,
                        followid = followId,
                        followyn = followYN
                    )
                )
            )
        )

    suspend fun postNewUserReport(
        reportId: String,
        reportType: String,
        reportContent: String,
        reportYN: Boolean
    ) = repository.postNewUserReport(
        accessTokenUtil, NewUserReportRequest(
            listOf(
                NewReportUser(
                    uuid = currentUUIDUtil,
                    reportid = reportId,
                    reporttype = reportType,
                    reportcontent = reportContent,
                    reportyn = reportYN
                )
            )
        )
    )

    suspend fun deleteMyFeed(
        feedNo: Int
    ) = repository.deleteMyFeed(
        accessTokenUtil, DeleteFeedRequest(
            listOf(
                RemoveActivityInfo(feedNo)
            )
        )
    )

    suspend fun getUserHistoryInfo() = repository.getUserHistory(
        accessTokenUtil, UserHistoryInfoRequest(
            listOf(
                AppHistoryInfo(
                    mileType = "A",
                    page = 1,
                    perpage = 5,
                    searchMonths = 12,
                    uuid = currentUUIDUtil
                )
            )
        )
    )

}