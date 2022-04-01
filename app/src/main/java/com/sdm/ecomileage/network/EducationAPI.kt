package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.education.diary.request.PostDiaryRequest
import com.sdm.ecomileage.model.education.diary.response.PostDiaryResponse
import com.sdm.ecomileage.model.education.educationInfo.request.EducationInfoRequest
import com.sdm.ecomileage.model.education.educationInfo.response.EducationInfoResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface EducationAPI {
    @POST("EducationInfo")
    suspend fun getEducationVideoInfo(
        @Header ("token") token: String,
        @Body body: EducationInfoRequest
    ) : EducationInfoResponse

    @POST("NewEducationInfo")
    suspend fun postDiary(
        @Header ("token") token: String,
        @Body body: PostDiaryRequest
    ) : PostDiaryResponse
}