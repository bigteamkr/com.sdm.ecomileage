package com.example.sdm_eco_mileage.repository.get

import android.util.Log
import com.example.sdm_eco_mileage.data.DataOrException
import javax.inject.Inject

//class GetRepository @Inject constructor(private val api: GetAPI) {
//    suspend fun getProfile(
//        userid: String
//    ): DataOrException<String, Boolean, Exception> {
//        val response = try {
//            api.getProfileImg(userid = userid)
//        } catch (e: Exception) {
//            Log.d("GetRepository", "getProfile: $e")
//            return DataOrException(e = e)
//        }
//
//        return DataOrException(data = response)
//    }
//
//}