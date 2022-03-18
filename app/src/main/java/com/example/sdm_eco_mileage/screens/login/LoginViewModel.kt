package com.example.sdm_eco_mileage.screens.login

import androidx.lifecycle.ViewModel
import com.example.sdm_eco_mileage.data.DataOrException
import com.example.sdm_eco_mileage.model.login.request.LoginRequest
import com.example.sdm_eco_mileage.model.login.response.LoginResponse
import com.example.sdm_eco_mileage.repository.loginRegisterFindRepository.LoginRegisterFindRepository
import com.example.sdm_eco_mileage.utils.accessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRegisterFindRepository) :
    ViewModel() {

    suspend fun getLogin(
        id: String,
        password: String
    ): DataOrException<LoginResponse, Boolean, Exception> =
        repository.getLogin(
            token = accessToken,
            LoginRequest(
                id = id,
                password = password,
                uuid = "59a1e164-8f55-4486-b8f9-6362892a94f4"
            )
        )
}