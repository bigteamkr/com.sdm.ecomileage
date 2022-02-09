package com.sdm.ecomileage.activities

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "d85ee5d1f65d1ef8a7d0979d37ebc3de")
    }
}