package com.sdm.ecomileage.screens.myPage

import androidx.lifecycle.ViewModel
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
import com.sdm.ecomileage.model.report.user.request.NewReportUser
import com.sdm.ecomileage.model.report.user.request.NewUserReportRequest
import com.sdm.ecomileage.repository.myPageRepository.MyPageRepository
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val repository: MyPageRepository) : ViewModel() {

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
}