package com.sdm.ecomileage.utils

import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.sdm.ecomileage.SdmEcoMileageApplication

//Todo : accessToken 과 userid 는 Constants 에 넣는게 맞는가, util 에 넣는게 맞는가?
object Constants {
    const val BASE_URL = "http://api.ecosdm.com/app/"
    const val DATA_STORE_FILE_NAME = "userInfo.pb"
    val appUpdateManager: AppUpdateManager? = AppUpdateManagerFactory.create(SdmEcoMileageApplication.ApplicationContext())
}