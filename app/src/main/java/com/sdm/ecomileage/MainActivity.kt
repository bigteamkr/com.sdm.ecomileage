package com.sdm.ecomileage

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sdm.ecomileage.navigation.SdmNavigation
import com.sdm.ecomileage.ui.theme.Sdm_eco_mileageTheme
import com.sdm.ecomileage.utils.kakaoNativeAppKey
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val context: Context = this.applicationContext

        setContent {
            Sdm_eco_mileageTheme {
                // A surface container using the 'background' color from the theme
                SecomiApp()

                KakaoSdk.init(this, kakaoNativeAppKey)
                // Todo : 주의 !!!!!!!!!!! JWPlayer 를 사용하기 위해서 AppCompat Theme -- values_themes 에서 확인 --  을 사용했음 !! UI 상의 변화는 모두 이때문 !!!!!!!!!!
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SecomiApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SdmNavigation()
        }
    }
}
