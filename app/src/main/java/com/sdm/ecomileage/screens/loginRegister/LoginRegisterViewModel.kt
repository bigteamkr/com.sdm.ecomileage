package com.sdm.ecomileage.screens.loginRegister

import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.R
import com.sdm.ecomileage.SdmEcoMileageApplication
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.appSettings.init.request.AppInit
import com.sdm.ecomileage.model.appSettings.init.request.AppInitRequest
import com.sdm.ecomileage.model.appSettings.init.response.AppInitResponse
import com.sdm.ecomileage.model.appSettings.refresh.request.AppRequestToken
import com.sdm.ecomileage.model.appSettings.refresh.request.AppRequestTokenRequest
import com.sdm.ecomileage.model.appSettings.refresh.response.AppRequestTokenResponse
import com.sdm.ecomileage.model.login.request.LoginRequest
import com.sdm.ecomileage.model.login.response.LoginResponse
import com.sdm.ecomileage.model.memberUpdate.request.AppMemberUpdate
import com.sdm.ecomileage.model.memberUpdate.request.MemberUpdateRequest
import com.sdm.ecomileage.model.memberUpdate.response.MemberUpdateResponse
import com.sdm.ecomileage.model.registerPage.register.request.AppRegister
import com.sdm.ecomileage.model.registerPage.register.request.RegisterRequest
import com.sdm.ecomileage.model.registerPage.register.response.RegisterResponse
import com.sdm.ecomileage.repository.loginRegisterFindRepository.LoginRegisterFindRepository
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.bitmapToString
import com.sdm.ecomileage.utils.currentUUIDUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(private val repository: LoginRegisterFindRepository) :
    ViewModel() {

    suspend fun postAppInit(
        uuid: String,
        refreshToken: String
    ) : DataOrException<AppInitResponse, Boolean, Exception> =
        repository.postAppInit(
            AppInitRequest(
                listOf(
                    AppInit(
                        osVersion = "Android",
                        appVersion = 163,
                        osType = 2,
                        uuid = uuid,
                        refreshToken = refreshToken
                    )
                )
            )
        )

    suspend fun getLogin(
        id: String,
        password: String,
        uuid: String
    ): DataOrException<LoginResponse, Boolean, Exception> =
        repository.getLogin(
            LoginRequest(
                id = id,
                password = password,
                uuid = uuid
            )
        )

    suspend fun getAppRequestToken(
        uuid: String,
        refreshToken: String
    ) : DataOrException<AppRequestTokenResponse, Boolean, Exception> =
        repository.getAppRequestToken(
            AppRequestTokenRequest(
                listOf(
                    AppRequestToken(
                        uuid = uuid,
                        refreshToken = refreshToken
                    )
                )
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
            accessTokenUtil, MemberUpdateRequest(
                listOf(
                    AppMemberUpdate(
                        uuid = currentUUIDUtil,
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

}