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

    var _videoURL = ""
    var _thumbnailUrl = ""
    var _point = 0
    var _educationsno = 0
    var _educationYN = false
    var _manageProfile = ""
    var _manageId = ""
    var _manageName = ""

    fun setVideo(
        videoURL: String,
        thumbnailURL: String,
        point: Int,
        educationNo: Int,
        educationYN: Boolean,
        manageProfile: String,
        manageId: String,
        manageName: String
    ) {
        _videoURL = videoURL
        _thumbnailUrl = thumbnailURL
        _point = point
        _educationsno = educationNo
        _educationYN = educationYN
        _manageProfile = manageProfile
        _manageId = manageId
        _manageName = manageName
    }

    private var _lastEducationThumbnail: String = ""
    fun getEducationThumbnail(): String = _lastEducationThumbnail
    fun putEducationThumbnail(thumbnail: String) {
        _lastEducationThumbnail = thumbnail
    }

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

