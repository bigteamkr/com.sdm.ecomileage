package com.sdm.ecomileage.screens.myPage

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfo
import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfoRequest
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import com.sdm.ecomileage.model.myPage.newFollow.request.NewFollowInfo
import com.sdm.ecomileage.model.myPage.newFollow.request.NewFollowInfoRequest
import com.sdm.ecomileage.model.myPage.userFeedInfo.request.UserFeedInfo
import com.sdm.ecomileage.model.myPage.userFeedInfo.request.UserFeedInfoRequest
import com.sdm.ecomileage.model.myPage.userFeedInfo.response.UserFeedInfoResponse
import com.sdm.ecomileage.repository.myPageRepository.MyPageRepository
import com.sdm.ecomileage.utils.accessToken
import com.sdm.ecomileage.utils.currentUUID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val repository: MyPageRepository) : ViewModel() {

    suspend fun getMyFeedInfo(): DataOrException<MyFeedInfoResponse, Boolean, Exception> =
        repository.getMyFeedInfo(
            accessToken, MyFeedInfoRequest(
                listOf(
                    MyFeedInfo(
                        lang = "ko",
                        uuid = currentUUID,
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
            accessToken, UserFeedInfoRequest(
                listOf(
                    UserFeedInfo(
                        uuid = currentUUID,
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
            accessToken, NewFollowInfoRequest(
                listOf(
                    NewFollowInfo(
                        uuid = uuid,
                        followid = followId,
                        followyn = followYN
                    )
                )
            )
        )

}