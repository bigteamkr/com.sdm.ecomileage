package com.sdm.ecomileage.screens.myPage

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfo
import com.sdm.ecomileage.model.myPage.myFeedInfo.request.MyFeedInfoRequest
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import com.sdm.ecomileage.repository.myPageRepository.MyPageRepository
import com.sdm.ecomileage.utils.accessToken
import com.sdm.ecomileage.utils.uuidSample
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
                        uuid = uuidSample,
                        page = 1,
                        perpage = 10,
                        order = "user",
                        desc = "10"
                    )
                )
            )
        )

}