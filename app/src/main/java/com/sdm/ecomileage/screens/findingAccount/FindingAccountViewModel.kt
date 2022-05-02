package com.sdm.ecomileage.screens.findingAccount

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.model.finding.id.request.AppUserAccoundFind
import com.sdm.ecomileage.model.finding.id.request.FindingIdRequest
import com.sdm.ecomileage.model.finding.password.request.AppUserAccountPasswordFind
import com.sdm.ecomileage.model.finding.password.request.FindingPasswordRequest
import com.sdm.ecomileage.repository.findingPageRepository.FindingRepository
import com.sdm.ecomileage.utils.currentUUIDUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindingAccountViewModel @Inject constructor(private val repository: FindingRepository) :
    ViewModel() {

    suspend fun getFindingId(userName: String, userTel: String) =
        repository.getFindingId(
            FindingIdRequest(
                AppUserAccoundFind = listOf(
                    AppUserAccoundFind(
                        userName = userName,
                        userTel = userTel,
                        uuid = currentUUIDUtil
                    )
                )
            )
        )

    suspend fun getFindingPassword(userName: String, userEmail: String) =
        repository.getFindingPassword(
            FindingPasswordRequest(
                AppUserAccountPasswordFind = listOf(
                    AppUserAccountPasswordFind(
                        userName = userName,
                        userId = userEmail
                    )
                )
            )
        )
}