package com.sdm.ecomileage.repository.educationRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.education.diary.request.PostDiaryRequest
import com.sdm.ecomileage.model.education.diary.response.PostDiaryResponse
import com.sdm.ecomileage.model.education.educationInfo.request.EducationInfoRequest
import com.sdm.ecomileage.model.education.educationInfo.response.EducationInfoResponse
import com.sdm.ecomileage.network.EducationAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class EducationRepository @Inject constructor(private val api: EducationAPI) {
    suspend fun getEducationVideoList(
        token: String,
        body: EducationInfoRequest
    ): DataOrException<EducationInfoResponse, Boolean, Exception> {
        val response = try {
            api.getEducationVideoInfo(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("EducationRepo", "getEducationVideoList: api call in repository didn't work")
            Log.d("EducationRepo", "getEducationVideoList: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    suspend fun postDiary(
        token: String,
        body: PostDiaryRequest
    ): DataOrException<PostDiaryResponse, Boolean, Exception> {
        val response = try {
            api.postDiary(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("EducationRepo", "postDiary: api call in repository didn't work")
            Log.d("EducationRepo", "postDiary: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

}