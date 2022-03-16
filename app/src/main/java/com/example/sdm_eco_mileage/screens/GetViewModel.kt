package com.example.sdm_eco_mileage.screens

import androidx.lifecycle.ViewModel
import com.example.sdm_eco_mileage.data.DataOrException
import com.example.sdm_eco_mileage.repository.get.GetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GetViewModel @Inject constructor(private val repository: GetRepository) : ViewModel() {
    suspend fun getProfileImg(userid: String): DataOrException<String, Boolean, Exception> =
        repository.getProfile(userid = userid)

}