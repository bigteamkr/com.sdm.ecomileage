package com.sdm.ecomileage.screens.homeAdd

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.homeAdd.request.HomeAddRequest
import com.sdm.ecomileage.model.homeAdd.response.HomeAddResponse
import com.sdm.ecomileage.repository.HomeRepository.HomeRepository
import com.sdm.ecomileage.utils.accessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.Q)
@HiltViewModel
class HomeAddViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    suspend fun postHomeFeedInfo(body: HomeAddRequest) : DataOrException<HomeAddResponse, Boolean, Exception> =
        repository.postHomeFeed(accessToken, body = body)

}