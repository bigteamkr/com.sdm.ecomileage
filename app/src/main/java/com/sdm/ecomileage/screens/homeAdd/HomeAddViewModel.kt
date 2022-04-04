package com.sdm.ecomileage.screens.homeAdd

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.challenge.newChallenge.newChallengeRequest.NewChallengeInfoRequest
import com.sdm.ecomileage.model.challenge.newChallenge.newChallengeResponse.NewChallengeInfoResponse
import com.sdm.ecomileage.model.homeAdd.request.HomeAddRequest
import com.sdm.ecomileage.model.homeAdd.response.HomeAddResponse
import com.sdm.ecomileage.repository.challengeRepository.ChallengeRepository
import com.sdm.ecomileage.repository.homeRepository.HomeRepository
import com.sdm.ecomileage.utils.accessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.Q)
@HiltViewModel
class HomeAddViewModel @Inject constructor(private val homeRepository: HomeRepository, private val challengeRepository: ChallengeRepository) : ViewModel() {

    suspend fun postHomeFeedInfo(body: HomeAddRequest) : DataOrException<HomeAddResponse, Boolean, Exception> =
        homeRepository.postHomeFeed(accessToken, body = body)

    suspend fun postNewChallengeInfo(body : NewChallengeInfoRequest) : DataOrException<NewChallengeInfoResponse, Boolean, Exception> =
        challengeRepository.postNewChallengeInfo(accessToken, body = body)
}