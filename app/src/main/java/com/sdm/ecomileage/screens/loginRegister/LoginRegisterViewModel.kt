package com.sdm.ecomileage.screens.loginRegister

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.sdm.ecomileage.R
import com.sdm.ecomileage.SdmEcoMileageApplication
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.login.request.LoginRequest
import com.sdm.ecomileage.model.login.response.LoginResponse
import com.sdm.ecomileage.model.memberUpdate.request.AppMemberUpdate
import com.sdm.ecomileage.model.memberUpdate.request.MemberUpdateRequest
import com.sdm.ecomileage.model.memberUpdate.response.MemberUpdateResponse
import com.sdm.ecomileage.model.register.request.AppRegister
import com.sdm.ecomileage.model.register.request.RegisterRequest
import com.sdm.ecomileage.model.register.response.RegisterResponse
import com.sdm.ecomileage.repository.loginRegisterFindRepository.LoginRegisterFindRepository
import com.sdm.ecomileage.utils.accessToken
import com.sdm.ecomileage.utils.bitmapToString
import com.sdm.ecomileage.utils.uuidSample
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    val defaultProfile = ResourcesCompat.getDrawable(
        SdmEcoMileageApplication.ApplicationContext().resources,
        R.drawable.ic_default_profile,
        null
    )

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

    suspend fun putMemberUpdate(
        userName: String? = null,
        userPhone: String? = null,
        userDept: String? = null,
        userAddress: String? = null,
        userAddressDtl: String? = null,
        userTown: String? = null,
        userZipcode: String? = null,
        isinfoagree: String? = null,
        regagree: String? = null,
        childagree: String? = null,
        profileImg: String? = null,
        pointsavetype: String?  = null
    ): DataOrException<MemberUpdateResponse, Boolean, Exception> =
        repository.putMemberUpdate(
            accessToken, MemberUpdateRequest(
                listOf(
                    AppMemberUpdate(
                        uuid = uuidSample,
                        userName = userName,
                        userPhone = userPhone,
                        userDept = userDept,
                        userAddress = userAddress,
                        userAddressdtl = userAddressDtl,
                        userTown = userTown,
                        userZipcode = userZipcode,
                        isinfoagree = isinfoagree,
                        regagree = regagree,
                        childagree = childagree,
                        profileImg = profileImg,
                        pointsavetype = pointsavetype
                    )
                )
            )
        )

    private var _profileBitmap: Bitmap? = null
    fun getProfileBitmap() = _profileBitmap

//    @Composable
//    fun imagePicker(context: Context): ManagedActivityResultLauncher<String, Uri?> {
//
//
//        return imagePickerLauncher
//    }


    var dataStore = repository.readProto.asLiveData()


    suspend fun updateUUID(
        uuid: String? = null
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateValue(uuid, null, null)
    }

    suspend fun updateId(
        loginId: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateValue(null, loginId, null)
    }

    suspend fun updateAutoLogin(
        loginId: String,
        loginPassword: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateValue(null, loginId, loginPassword)
    }


}