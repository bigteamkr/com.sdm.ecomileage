package com.sdm.ecomileage.screens.mileageChange

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.model.mileagechange.request.MileageChangeRequest
import com.sdm.ecomileage.model.mileagechange.response.MileageChangeResponse
import com.sdm.ecomileage.network.MileageChangeAPI
import com.sdm.ecomileage.repository.mileageChangeRepository.MileageChangeRepository
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MileageChangeModelAndView @Inject constructor
    (private val repository: MileageChangeRepository,
     private val api : MileageChangeAPI
    ) : ViewModel() {

    suspend fun postMileageChange(
        uuid: String, usagetype: String, gifttype: String, persno: String
    ) {
        repository.postMileageChangeRepo(
            accessTokenUtil, MileageChangeRequest(
                uuid = currentUUIDUtil,
                usagetype = usagetype,
                gifttype = gifttype,
                persno = persno
            )
        )
    }
}

