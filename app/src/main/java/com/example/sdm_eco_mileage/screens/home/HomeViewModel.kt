package com.example.sdm_eco_mileage.screens.home

import androidx.lifecycle.ViewModel
import com.example.sdm_eco_mileage.data.DataOrException
import com.example.sdm_eco_mileage.model.homeInfo.request.HomeInfo
import com.example.sdm_eco_mileage.model.homeInfo.request.HomeInfoRequest
import com.example.sdm_eco_mileage.model.homeInfo.response.HomeInfoResponse
import com.example.sdm_eco_mileage.repository.HomeRepository.HomeRepository
import com.example.sdm_eco_mileage.utils.accessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    suspend fun postHomeInfo(): DataOrException<HomeInfoResponse, Boolean, Exception> =
        repository.postHomeInfo(
            accessToken, HomeInfoRequest(
                HomeInfo = listOf(
                    HomeInfo("admin@email.com")
                )
            )
        )
}