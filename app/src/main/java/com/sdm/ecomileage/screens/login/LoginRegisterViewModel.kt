package com.sdm.ecomileage.screens.login

import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.R
import com.sdm.ecomileage.SdmEcoMileageApplication
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.login.request.LoginRequest
import com.sdm.ecomileage.model.login.response.LoginResponse
import com.sdm.ecomileage.model.register.request.AppRegister
import com.sdm.ecomileage.model.register.request.RegisterRequest
import com.sdm.ecomileage.model.register.response.RegisterResponse
import com.sdm.ecomileage.repository.loginRegisterFindRepository.LoginRegisterFindRepository
import com.sdm.ecomileage.utils.bitmapToString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(private val repository: LoginRegisterFindRepository) :
    ViewModel() {

    suspend fun getLogin(
        id: String,
        password: String
    ): DataOrException<LoginResponse, Boolean, Exception> =
        repository.getLogin(
            LoginRequest(
                id = id,
                password = password,
                uuid = "59a1e164-8f55-4486-b8f9-6362892a94f4"
            )
        )




    private val defaultProfile = ResourcesCompat.getDrawable(SdmEcoMileageApplication.ApplicationContext().resources, R.drawable.ic_default_profile, null)

    suspend fun postRegister(
        userName: String,
        userPwd: String,
        email: String,
        userDept: String,
        userAddress: String
    ): DataOrException<RegisterResponse, Boolean, Exception> =
        repository.postRegister(
            RegisterRequest(
                AppRegister = listOf(
                    AppRegister(
                        userName = userName,
                        userPwd = userPwd,
                        email = email,
                        userDept = userDept,
                        userAddress = userAddress,
                        isinfoagree = "Y",
                        childagree = "Y",
                        profileImg = bitmapToString(defaultProfile!!.toBitmap())
                    )
                )
            )
        )
}