package com.example.sdm_eco_mileage.screens.homeAdd

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.sdm_eco_mileage.data.DataOrException
import com.example.sdm_eco_mileage.model.homeAdd.request.HomeAddRequest
import com.example.sdm_eco_mileage.model.homeAdd.response.HomeAddResponse
import com.example.sdm_eco_mileage.repository.HomeRepository.HomeRepository
import com.example.sdm_eco_mileage.utils.accessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.Q)
@HiltViewModel
class HomeAddViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    suspend fun postHomeFeedInfo(body: HomeAddRequest) : DataOrException<HomeAddResponse, Boolean, Exception> =
        repository.postHomeFeed(accessToken, body = body)

}