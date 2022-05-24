package com.sdm.ecomileage.screens.education

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.education.diary.request.NewEducationInfo
import com.sdm.ecomileage.model.education.diary.request.PostDiaryRequest
import com.sdm.ecomileage.model.education.diary.response.PostDiaryResponse
import com.sdm.ecomileage.model.education.educationInfo.request.EducationInfo
import com.sdm.ecomileage.model.education.educationInfo.request.EducationInfoRequest
import com.sdm.ecomileage.model.education.educationInfo.response.EducationInfoResponse
import com.sdm.ecomileage.repository.educationRepository.EducationRepository
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EducationViewModel @Inject constructor(private val repository: EducationRepository) :
    ViewModel() {

    suspend fun getEducationVideoList(): DataOrException<EducationInfoResponse, Boolean, Exception> =
        repository.getEducationVideoList(
            accessTokenUtil, EducationInfoRequest(
                EducationInfo = listOf(
                    EducationInfo(
                        uuid = currentUUIDUtil,
                        page = 1,
                        perpage = 100
                    )
                )
            )
        )

    suspend fun postDiary(
        educationNo: Int,
        content: String
    ): DataOrException<PostDiaryResponse, Boolean, Exception> =
        repository.postDiary(
            accessTokenUtil, PostDiaryRequest(
                NewEducationInfo = listOf(
                    NewEducationInfo(
                        uuid = currentUUIDUtil,
                        educationsno = educationNo,
                        viewcontent = content,
                        viewstatus = "100"
                    )
                )
            )
        )
}